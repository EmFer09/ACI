import requests
from bs4 import BeautifulSoup
import pandas as pd
import mysql.connector
import os 
from dotenv import load_dotenv
from datetime import datetime

url = "https://cinesembajadores.es/madrid/"

#Estos datos serán necesarios para la tabla eventos en la BD pero no están disponibles en la web, por lo que los añado manualmente
categoria = "película" 
sala_id = 1   

response = requests.get(url)
response.raise_for_status()
soup = BeautifulSoup(response.content, "html.parser")

#el contenedor que muestra los datos de título y descripción de las películas es un div con clase "pelicula"
peliculas = soup.find_all('div', class_="pelicula")

#Se escriben las columnas de la tabla eventos para que se poblen con los daros recogidos se exporten en el orden necesario al archivo .csv
columnas = ['nombre', 'sala_id', 'fecha_inicio', 'categoria', 'descripcion', 'hora' ] 
data = []

#se usan bucles for para que se recorra la lista de películas en su totalidad
for pelicula in peliculas:
    nombre = pelicula.find('h2').text
    descripcion = pelicula.find('div', class_="sinopsis").text

    #pero en este caso la información sobre fecha y hora no está en el mismo contenedor, por lo que hay que buscarla con su respectiva clase ("showtimelist")
    horarios = pelicula.parent.find_all('div', class_="showtimelist")

    hora = []
    dia = []
    anio = datetime.now().year

    for horario in horarios:
        #Se ubican los elementos que contienen la fecha y hora (<p> con atributos "data-dia" y "data-hora") y se obtienen sus valores para almacenarlos en las listas hora y dia
        sesiones = horario.find_all('p', attrs={"data-hora": True})
        hora.extend([h.get("data-hora") for h in horario.find_all('p', attrs={"data-hora": True})])
        dia.extend([d.get("data-dia") for d in horario.find_all('p', attrs={"data-dia": True})])

    if hora and dia:
        print(f"Título: {nombre}")
        print(f"Sinopsis: {descripcion}")

        #se han guardado los valores de hora y dia en listas por lo que para mostrarlos y guardarlos se usa zip()
        for h, d in zip(hora, dia):
                dia_int = int(d.split("/")[0])
                mes_int = int(d.split("/")[1])
                fecha_inicio = f"{anio}-{mes_int:02d}-{dia_int:02d}"
                print(f"Fecha: {fecha_inicio}")
                print(f"Horario: {h}")

                #se mueven los datos recogidos a la lista data declarada al principio para que se puedan exportar al .csv
                data.append([nombre, sala_id, fecha_inicio, categoria, descripcion, h])
        print("-" * 40) 

    #se recoge la operación en un if else por si en algún caso falla la operación para obtener dia y hora 
    else:
        print("No hay información disponible.")

#se usa pandas para crear un dataframe con los datos recogidos en la lista data que los identifique con las columnas para exportar los datos en el orden que interesa
df = pd.DataFrame(data, columns=columnas)
df.to_csv("webScraper/datos/eventos1.csv", index=False, encoding="utf-8-sig")

#Ahora se crea el conector a la base de datos protegiando las claves en un archivo .env
load_dotenv()

conexion = mysql.connector.connect(
     host = os.getenv("DB_HOST"),
     user = os.getenv("DB_USER"),
     password = os.getenv("DB_PASSWORD"),
     database = os.getenv("DB_NAME")
)

cursor = conexion.cursor()

csv_file = 'webScraper/datos/eventos1.csv'

datos = pd.read_csv(csv_file)

for index, row in datos.iterrows():
    cursor.execute(
        "INSERT INTO eventos (nombre, sala_id, descripcion, fecha_inicio, hora, categoria) VALUES (%s, %s, %s, %s, %s, %s)",
        (row['nombre'], row['sala_id'], row['descripcion'], row['fecha_inicio'], row['hora'], row['categoria'])
    )

conexion.commit()
print("Todo ok")

cursor.close()
conexion.close()