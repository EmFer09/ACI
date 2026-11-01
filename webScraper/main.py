import requests
import csv
from bs4 import BeautifulSoup

url = "https://cinesembajadores.es/madrid/"

response = requests.get(url)
response.raise_for_status()
soup = BeautifulSoup(response.content, "html.parser")

horarios = soup.find_all('div', class_="showtimelist")

for horario in horarios[:2]:
    print(horario.prettify()[:1500])
""" 
peliculas = soup.find_all('div', class_="pelicula")
for pelicula in peliculas:
    titulo = pelicula.find('h2').text
    descripcion = pelicula.find('div', class_="info").text

    horarios = pelicula.find_all('div', class_="showtimelist")
    print(f"Título: {titulo}")
    print(f"Horarios encontrados: {len(horarios)}")
    if horarios:
        for horario in horarios:
            print(horario.prettify()[:1000])
            for horario_diahora in horario.find_all('p', attrs={"data-dia": True, "data-hora": True}):
                fecha = horario_diahora.get("data-dia")
                hora = horario_diahora.get("data-hora")

                print(f"Título: {titulo}")
                print(f"Sinopsis: {descripcion}")
                print(f"Fecha: {fecha}")
                print(f"Horario: {hora}")
                print("-" * 40)

        
    else:
        print(f"Título: {titulo}")
        print(f"Sinopsis: {descripcion}")
        print("No hay horarios disponibles.")
   """     





 