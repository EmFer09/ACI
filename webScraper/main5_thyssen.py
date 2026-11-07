import requests
from bs4 import BeautifulSoup
import pandas as pd
import mysql.connector
import os 
from dotenv import load_dotenv
from datetime import datetime

url = "https://www.museothyssen.org/exposiciones"


response = requests.get(url)
response.raise_for_status()
soup = BeautifulSoup(response.content, "html.parser")

#El mes dentro de la fecha también aparece escrito en texto
meses = {
    "enero": "01",
    "febrero": "02",
    "marzo": "03",
    "abril": "04",
    "mayo": "05",
    "junio": "06",
    "julio": "07",
    "agosto": "08",
    "septiembre": "09",
    "octubre": "10",
    "noviembre": "11",
    "diciembre": "12"
}

"""
#El método find() no encontraba nada por lo que se usa select() según la documentación de BeautifulSoup
expos = soup.select(".snippet__kicker + .snippet__title")
for expo in expos:
    nombre = expo.get_text(strip=True)
    #el último elemento que encuentra no es realmente una exposición, además el snippet__kicker no indica que lo sea (el texto del div debe ser "Exposición temporal")
    entonces realmente en vez de buscar por que el elemento tenga un snippt_title, hay que buscar basándose en que el snippet_kicker tenga ese texto
"""

actuales = soup.find("div", class_="field--string u-display-inline", string="Actuales")

columnas = ['nombre', 'sala_id', 'fecha_inicio', 'fecha_final', 'categoria', 'descripcion']
data = []

categoria = "expo" 
sala_id = 5 

#Las exposiciones futuras aparecen en otro div snipper_kicker después de actuales y su fecha tiene un formato que rompe los demás
if actuales:
    fecha_inicio = None
    fecha_final = None
    for kicker in soup.select(".snippet__kicker"):
        if kicker.get_text(strip=True) == "Exposición temporal":
            nombre = kicker.find_next_sibling("div", class_="snippet__title")
            if nombre:
                nombre = nombre.get_text(" ", strip=True)
                print(nombre)

                fecha = kicker.find_next_sibling("div", class_="snippet__text")
                if fecha:
                    partes_fecha = fecha.get_text(strip=True).split(" ")
                    
                    fecha_start = partes_fecha[1:6]
                    fecha_end = partes_fecha[7:12]

                    #print(fecha_start)
                    #print(fecha_end)

                    if fecha_start[4].isdigit() and len(fecha_start[4]) == 4:
                        dia_start = fecha_start[0]
                        mes_start = meses[fecha_start[2]]
                        anio_start = fecha_start[4]
                        fecha_inicio = f"{anio_start}-{mes_start}-{int(dia_start):02d}"
                        print(f"Fecha inicio: {fecha_inicio}")

                        dia_end = fecha_end[0]
                        mes_end = meses[fecha_end[2]]
                        anio_end = fecha_end[4]
                        fecha_final = f"{anio_end}-{mes_end}-{int(dia_end):02d}"
                        print(f"Fecha final: {fecha_final}")
                        #antes del apend para mandar los datos al csv poner una condición de que tenga fecha para que se guarde para que no aparezcan las exposiciones futuras

                        #si hay datos de fechas (o sea, si se cumple la condición de que la posición 4 sea un número de 4 dígitos = que no sea de las exposiciones futuras) se busca la descripción
                        if fecha_inicio and fecha_final:
                            enlace = kicker.find_parent("a")
                            descripcion = enlace.get("href")
                            print(descripcion)

                            if descripcion:
                                data.append([nombre, sala_id, fecha_inicio, fecha_final, categoria, descripcion])

print(data)
print(len(data))

df = pd.DataFrame(data, columns=columnas)
df.to_csv("webScraper/datos/eventos5.csv", index=False, encoding="utf-8-sig")

load_dotenv()

conexion = mysql.connector.connect(
     host = os.getenv("DB_HOST"),
     user = os.getenv("DB_USER"),
     password = os.getenv("DB_PASSWORD"),
     database = os.getenv("DB_NAME")
)

cursor = conexion.cursor()

csv_file = 'webScraper/datos/eventos5.csv'

datos = pd.read_csv(csv_file)

for index, row in datos.iterrows():
    cursor.execute(
        "INSERT INTO eventos (nombre, sala_id, fecha_inicio, fecha_final, categoria, descripcion) VALUES (%s, %s, %s, %s, %s, %s)",
        (row['nombre'], row['sala_id'], row['fecha_inicio'], row['fecha_final'], row['categoria'], row['descripcion'])
    )

conexion.commit()
print("Todo ok")

cursor.close()
conexion.close()
