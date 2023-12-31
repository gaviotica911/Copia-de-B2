from faker import Faker
from faker.providers import DynamicProvider
from datetime import datetime
from datetime import datetime, timedelta
from datetime import datetime, timezone

import random

fake = Faker('es_CO')
fecha_inicial = datetime(1990, 1, 1)

# Definir la fecha final (límite hasta el año 2024)
fecha_final = datetime(2024, 12, 31)
reservas=[]
consumos=[]
habitaciones=[]

class Tiposhab:
    def __init__(self, id):  # Fixed typo here (__init__ instead of _init_)
        self.tipo= fake.random_element(elements=['suite', 'suite presidencial', 'familiar', 'doble','sencilla'])
        self.descripcion = fake.text(max_nb_chars=50)
        self.id = id

    def sql_add(self):
        return f"INSERT INTO tiposhab (tipo, descripcion, id) VALUES ('{self.tipo}', '{self.descripcion}', {self.id});"

class Habitacion:
    def __init__(self, id):  # Fixed typo here (__init__ instead of _init_)
        self.id = id
        self.capacidad = fake.random_int(min=1, max=8, step=1)
        self.tipoid = fake.random_int(min=1, max=5, step=1)

    def sql_add(self):
        return f"INSERT INTO habitacion (id, capacidad, tipoid) VALUES ({self.id}, {self.capacidad}, {self.tipoid});"

"""
fecha_inicial = datetime(2022, 1, 1)

# Definir la fecha final (límite hasta el año 2024)
fecha_final = datetime(2024, 12, 31)

# Generar una fecha aleatoria entre la fecha inicial y la fecha final
fecha_aleatoria = fecha_inicial + (fecha_final - fecha_inicial) * random.random()
"""
class Reservas:
    
    def __init__(self):  # Fixed typo here (__init__ instead of _init_)
        fechaentrada = fecha_inicial + (fecha_final - fecha_inicial) * random.random()

# Suponiendo que fecha_final es la última fecha posible (ej. 31 de diciembre del mismo año)
        fin_de_año = datetime(fechaentrada.year, 12, 31)

# Generar una fecha aleatoria entre fechaentrada y fin_de_año.
        if (fin_de_año - fechaentrada).days > 0:
            dias_random = random.randint(1, (fin_de_año - fechaentrada).days)
        else:
            # Si no hay diferencia de días, usa el mismo día
            dias_random = 0
        fechasalida = fechaentrada + timedelta(days=dias_random)
  
      
        self.fechaentrada = fechaentrada.strftime('%Y-%m-%dT%H:%M:%SZ')
        self.fechasalida = fechasalida.strftime('%Y-%m-%dT%H:%M:%SZ')
        self.numpersonas = fake.random_int(min=1, max=6, step=1)
        self.estado = fake.random_element(elements=['true', 'false'])
        precio_entero = random.randint(1000, 1000000)
        decimales = random.randint(1, 99)
        self.precio=float(f"{precio_entero}.{decimales:02d}")
        self.doc=fake.numerify(text='#########')
        
        
        #self.habitacionid = habitaciones[fake.random_int(min=0, max=len(habitaciones), step=1)]
        
   
         
   
    
    def sql_add(self):
        #return '{'+ f"'fechaentrada':  new Date('{self.fechaentrada}'), 'fechasalida': , 'numpersonas': , 'estado': , 'precioreserva': , 'docUsuario' :  ,'habitacionId':", "} "
        return f"'fechaEntrada': new Date('{self.fechaentrada}'), 'fechaSalida': new Date('{self.fechasalida}'), 'numPersonas': {self.numpersonas} , 'estado':{self.estado} , 'precioReserva': {self.precio}, 'docUsuario' : '{self.doc}' , 'consumos':[] " #,'habitacionId': ObjectId('{habitacionid}')"
    
    

class Consumos:
    def __init__(self):
  
        
        fecha=fecha_inicial + (fecha_final - fecha_inicial) * random.random() 
        
        self.fecha=fecha.strftime('%Y-%m-%dT%H:%M:%SZ')
        #self.idReserva=reservas[fake.random_int(min=0, max=len(reservas), step=1)]
        precio_entero = random.randint(1000, 1000000)
        self.idReserva=1
        decimales = random.randint(1, 99)
        self.precio=float(f"{precio_entero}.{decimales:02d}")
        
    def getFechaConsumoPorID(self):
        return self.fecha
        
    def sql_add(self):
       # a= f"'id': '{self.id}', 'fecha':new Date('{self.fecha}'), 'precio': {self.precio} "   #" ,'idReserva': ObjectId('{self.idReserva}')"
        #return ("db.consumos.insertOne({"+ a+     "});")
        return  f" 'fecha':new Date('{self.fecha}'), 'precio': {self.precio} "  
    def add_consumo(self):
        
        a=f"_id: ObjectId('{self.idReserva}') "
        b=f"consumos: '{self.id}' "
        
        return ("db.reservas.update({" + a + "},{$push: { " + b + "} });")

class Producto:
    def __init__(self):
        
        self.nombre = fake.word()
        self.descripcion= fake.text(max_nb_chars=50)
    def sql_add(self):
        a= f"'nombre': '{self.nombre}', 'descripcion': '{ self.descripcion}' "
        
        return ("{" + a + "}")
class Servicio:
    def __init__(self, id):
        self.id = id
        self.descripcion = fake.random_element(elements=['Piscina', 'Gimnasio', 'Internet', 'Lavado, planchado','Restaurante', 'Bar', 'Super Mercado', 'Tiendas', 'SPA', 'Utensilios', 'Salon de reuniones', 'Salon de conferencias'])
        self.precio = random.randint(1000,1000000)
        self.consumoid = fake.random_int(min=1, max=46000, step=1)
    def sql_add(self):
        return f"INSERT INTO servicio (id, descripcion, precio, consumoid) VALUES ({self.id}, '{self.descripcion}', {self.precio}, {self.consumoid});"
class Tienda:
    def __init__(self, id):
        self.nombre = fake.text(max_nb_chars=15)
        self.horarioapertura = self.generar_hora_inicial()
        self.horariocierre = self.generar_hora_final()
        self.capacidad = random.randint(20,150)
        self.id = id
    def generar_hora_inicial(self):
        hora_aleatoria = random.randint(0, 12)
        minutos_aleatorios = random.randint(0, 59)
        return f"{hora_aleatoria:02d}:{minutos_aleatorios:02d}" 
    def generar_hora_final(self):
        hora_aleatoria = random.randint(13, 23)
        minutos_aleatorios = random.randint(0, 59)
        return f"{hora_aleatoria:02d}:{minutos_aleatorios:02d}" 
    def sql_add(self):
        return f"INSERT INTO tiendas (nombre, horarioapertura, horariocierre, capacidad, id) VALUES ('{self.nombre}', TO_DATE('{self.horarioapertura}', 'HH24:MI'), TO_DATE('{self.horariocierre}', 'HH24:MI'), {self.capacidad}, {self.id});"
class Mercado:
    def __init__(self, id):
        self.nombre = fake.text(max_nb_chars=15)
        self.horarioapertura = self.generar_hora_inicial()
        self.horariocierre = self.generar_hora_final()
        self.capacidad = random.randint(20,150)
        self.id = id
    def generar_hora_inicial(self):
        hora_aleatoria = random.randint(0, 12)
        minutos_aleatorios = random.randint(0, 59)
        return f"{hora_aleatoria:02d}:{minutos_aleatorios:02d}" 
    def generar_hora_final(self):
        hora_aleatoria = random.randint(13, 23)
        minutos_aleatorios = random.randint(0, 59)
        return f"{hora_aleatoria:02d}:{minutos_aleatorios:02d}" 
    def sql_add(self):
        return f"INSERT INTO smercados (nombre, horarioapertura, horariocierre, capacidad, id) VALUES ('{self.nombre}', TO_DATE('{self.horarioapertura}', 'HH24:MI'), TO_DATE('{self.horariocierre}', 'HH24:MI'), {self.capacidad}, {self.id});"



        
# Function to generate fake data and print SQL insert statements
def populate(n):
    
    """
    for i in range(1, n + 1):
        x = Habitacion(i)
        print(x.sql_add())
    

    
    print("db.reservas.insertMany([ ")
 
       
    for i in range(n):
        if i<n-2:
            x = Reservas()
            reservas.append(x)
            print("{", x.sql_add(), "},")
        elif i==n-1:
            x = Reservas()
            reservas.append(x)
            print("{", x.sql_add(), "}")
        
    print("]);")
    
    """
    print("db.consumos.insertMany([ ")
 
       
    for i in range(n):
        if i<n-2:
            x = Consumos()
           
            print("{", x.sql_add(), "},")
        elif i==n-1:
            x = Consumos()
           
            print("{", x.sql_add(), "}")
        
    print("]);")
    """
    
    for i in range(1, n + 1):
        x = Producto()
        print(x.sql_add())
    for i in range(1, n + 1):
        x = Servicio(i)
        print(x.sql_add())
    for i in range(1, n + 1):
        x = Tienda(i)
        print(x.sql_add())
    for i in range(1, n + 1):
        x = Mercado(i)
    
        print(x.sql_add())
   
"""
      
def populateTipos(n):
    for i in range(1, n + 1):
        x = Tiposhab(i)
        print(x.sql_add())
    

nombres_de_tiendas_reales = [
    "Gucci",
    "Adidas",
    "Nike",
    "Apple",
    "Samsung",
    "Amazon",
    "Louis Vuitton",
    "Chanel",
    "Zara",
    "H&M",
    "Prada",
    "Rolex",
    "Google",
    "Microsoft",
    "Coca-Cola",
    "Toyota",
    "Sony",
    "L'Oreal",
    "Starbucks",
    "McDonald's",
    "Burberry",
    "Puma",
    "Ferrari",
    "Lamborghini",
    "Mercedes-Benz",
    "Volkswagen",
    "Disney",
    "Lego",
    "IBM",
    "Tesla"
]

class Bar:
    def __init__(self, id):
        self.id = id
        self.nombre = fake.random_element(elements=nombres_de_tiendas_reales)
        fecha = fecha_inicial + timedelta(seconds=(fecha_final - fecha_inicial).total_seconds() * random.random())
        self.horarioapertura = fecha.strftime('%Y-%m-%dT%H:%M:%SZ')
        self.horariocierre = (fecha + timedelta(hours=random.randint(1, 8))).strftime('%Y-%m-%dT%H:%M:%SZ')
        self.capacidad = fake.random_int(min=15, max=200, step=1)
        self.platosybebidas = [{"nombre": fake.text(max_nb_chars=15), "descripcion": fake.text(max_nb_chars=50)} for _ in range(random.randint(1, 5))]

    def json_data(self):
        return {
            "nombre": self.nombre,
            "horarioapertura": f"new Date('{self.horarioapertura}')",
            "horariocierre": f"new Date('{self.horariocierre}')",
            "capacidad": self.capacidad,
            "productos": self.platosybebidas
        }
# Crear instancias de Bar y obtener datos en formato JSON
bars_data = [bar.json_data() for bar in [Bar(str(i)) for i in range(1, 16)]]

# Imprimir los datos generados en el formato deseado
print("db.tiendas.insertMany([")
for i, bar_data in enumerate(bars_data):
    if i < len(bars_data) - 1:
        print(f"{bar_data},")
    else:
        print(f"{bar_data}")
print("]);")


class Servicio:
    def __init__(self, id):
        self.id = id
        self.descripcion = fake.text(max_nb_chars=50)
        self.tipo = fake.random_element(elements=['Piscina', 'Gimnasio', 'Internet', 'Lavado, planchado', 'SPA', 'Utensilios', 'Salon de reuniones', 'Salon de conferencias', 'Parqueadero', 'Servicio a la habitacion', 'Servicio de tintoreria', 'Servicio de planchado', 'Servicio de traslado', 'Servicio de despertador', 'Servicio de limpieza en seco'])
        self.duracion = fake.random_int(min=1, max=8, step=1)
    def sql_add(self):
        return f"descripcion: '{self.descripcion}', tipo: '{self.tipo}', duracion: {self.duracion}"


def populateServicios(n):
    print("db.Servicios.insertMany([ ")
    for i in range(n):
        if i<n-2:
            x = Servicio(i)
            print("{", x.sql_add(), "},")
        elif i==n-1:
            x = Servicio(i)
            print("{", x.sql_add(), "}")
    print("]);")

    
        

# Call the populate function to generate data for 10 users

populate(100)