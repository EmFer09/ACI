import requests
from bs4 import BeautifulSoup
import pandas as pd
import mysql.connector
import os 
from dotenv import load_dotenv

url= "https://www.circulobellasartes.com/cine-estudio/"

categoria="película" 
sala_id=2

response = requests.get(url)
response.raise_for_status()

soup = BeautifulSoup(response.content, "html.parser")

#en este caso, los datos que interesan están todos en un contenedor <div> con clase "cba_cine_table_container" que a su vez tiene otros contenedores con divs de los datos título, hora y día
contenedor = soup.find_all('div', class_="cba_cine_table_container")
#esta web no ofrece información con la sinopsis o descripción de cada película entonces se declara una variable vacía para este campo (en la tabla eventos esta columna puede ser NULL)
descripcion =""

columnas = ['nombre', 'sala_id', 'fecha_inicio', 'categoria', 'descripcion', 'hora']
data = []

for contenedor in contenedor:
    titulo = contenedor.find_all('div', class_="cba_cine_table_titulo")
    hora = contenedor.find_all('div', class_="cba_cine_table_hora")
    dia = contenedor.find('div', class_="cba_cine_table_dia")

    #el reto de esta página es que la fecha aparece como "4, Nov" en vez de "4/11" entonces hay que transformar el mes "Nov" a un valor que más adelante MySQL reconozca 
    #por lo que se crea un diccionario que recoja el nombre del mes como aparece en la web y lo identifique con un número de mes 
    meses = {
        "Ene": "01",
        "Feb": "02",
        "Mar": "03",
        "Abr": "04",
        "May": "05",
        "Jun": "06",
        "Jul": "07",
        "Ago": "08",
        "Sep": "09",
        "Oct": "10",
        "Nov": "11",
        "Dic": "12"
    }

    #se obtiene el valor de la fecha completa y se transforma a string
    dia_string = dia.get_text(strip=True)
    #se separa el dia_string en dos partes separadas por una coma, guardando la segunda parte en dia_mes
    dia_mes = dia_string.split(", ")[1]
    #dia_mes se separa en dos partes más, guardando la primera en dia_num (que corresponde al día del mes) y la segunda en mes (que sería en este caso "Nov")
    dia_num, mes = dia_mes.split()
    #se construye la fecha en formato "YYYY-MM-DD" usando el año 2026, el mes transformado a número gracias al diccionario meses y el día con dos dígitos
    fecha_inicio = f"2026-{meses[mes]}-{int(dia_num):02d}"

    
    if titulo and hora and dia:
        for t, h in zip(titulo, hora):
            nombre= t.find('a').get_text(strip=True)
            hora= h.get_text(strip=True)
            print(f"Título: {nombre}")
            print(f"Fecha: {fecha_inicio}")
            print(f"Hora: {hora}")
            print("-" * 40)
            data.append([nombre, sala_id, fecha_inicio, categoria, descripcion, hora])

    else:
        print("No hay información disponible.")

df = pd.DataFrame(data, columns=columnas) 
df.to_csv("webScraper/datos/eventos2.csv", index=False, encoding="utf-8-sig")
print("Todo bien con el scraper")

#Ahora se crea el conector a la base de datos protegiando las claves en un archivo .env
load_dotenv()

conexion = mysql.connector.connect(
     host = os.getenv("DB_HOST"),
     user = os.getenv("DB_USER"),
     password = os.getenv("DB_PASSWORD"),
     database = os.getenv("DB_NAME")
)

cursor = conexion.cursor()

csv_file = 'webScraper/datos/eventos2.csv'

datos = pd.read_csv(csv_file)
#el valor vacío de la columna descripción provoca un error al migrar a MySQL ya que Python lo interpreta como Nan y no se traduce a NULL en MySQL

for index, row in datos.iterrows():
    cursor.execute(
        "INSERT INTO eventos (nombre, sala_id, fecha_inicio, hora, categoria) VALUES (%s, %s, %s, %s, %s)",
        (row['nombre'], row['sala_id'], row['fecha_inicio'], row['hora'], row['categoria'])
    )

conexion.commit()
print("Todo ok")

cursor.close()
conexion.close() 