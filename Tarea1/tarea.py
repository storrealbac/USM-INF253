import re
import numpy as np
from PIL import Image

# Expresiones regulares
RE_Avanzar      = "(Avanzar( \d+)?)"
RE_Direccion    = "(Izquierda|Derecha)"
RE_Colores      = "(Rojo|Verde|Azul|Negro|Blanco)"
RE_RGB          = "(RGB\(\d{1,3},\d{1,3},\d{1,3}\))"
RE_Repetir      = "(Repetir \d+ veces( {)*)"
RE_Pintar       = f"(Pintar ({RE_Colores}|{RE_RGB}))"
RE_Llaves       = "(})|(\s*{)"
RE_Espacios     = "(\s)"
RE_Numero       = "\d+"
RE_Digitos      = "(\d+)+"

# Regex que abarca todos los anteriores para considerar una linea valida
RE_Global = "("
RE_Global += RE_Avanzar + "|"
RE_Global += RE_Direccion + "|"
RE_Global += RE_Colores + "|"
RE_Global += RE_RGB + "|"
RE_Global += RE_Repetir + "|"
RE_Global += RE_Pintar + "|"
RE_Global += RE_Llaves + "|"
RE_Global += RE_Espacios
RE_Global += ")*"

# Compilamos los Regex necesarios
RE_Global   = re.compile(RE_Global)
RE_Direccion= re.compile(RE_Direccion)
RE_RGB      = re.compile(RE_RGB)
RE_Colores  = re.compile(RE_Colores)
RE_Numero   = re.compile(RE_Numero)
RE_Digitos  = re.compile(RE_Digitos)
RE_Avanzar  = re.compile(RE_Avanzar)

"""
    Funciones necesarias para el correcto funcionamiento
"""

# Codigo del Github
def MatrizAImagen(matriz, filename='pixelart.png', factor=10):
    """
    Convierte una matriz de valores RGB en una imagen y la guarda como un archivo png.
    Las imagenes son escaladas por un factor ya que con los ejemplos se producirian imagenes muy pequeñas.
        Parametros:
                matriz (lista de lista de tuplas de enteros): Matriz que representa la imagen en rgb.
                filename (str): Nombre del archivo en que se guardara la imagen.
                factor (int): Factor por el cual se escala el tamaño de las imagenes.
    """
    matriz = np.array(matriz, dtype=np.uint8)
    np.swapaxes(matriz, 0, -1)

    N = np.shape(matriz)[0]

    img = Image.fromarray(matriz, 'RGB')
    img = img.resize((N*10, N*10), Image.Resampling.BOX)
    img.save(filename)

def prevPos(line, pos, tokens):
    """
        Permite encontrar la posición anterior válida dentro de la lista de tokens
        para así evitar los saltos de linea, errores de out of bounds etc.

        Parametros:
            line (int): Linea actual
            pos (pos): Posición actual en la linea
            tokens ([list]): Lista de tokens del archivo codigo.txt
        Retorno:
            (int, int) Devuelve una 2-tupla con la siguiente posición que corresponda <(line, pos) nueva>
    """

    # Al restarle uno quedaria fuera de los bounds
    # asi que veremos si podemos ir a la linea anterior
    if pos == 0:
        while True:
            # Si la linea es la primera, va tirar un error
            if line-1 <= -1:
                return (-1, -1)
            
            # Si es una linea vacia, retroce a la anteriior
            if tokens[line-1] == []:
                line -= 1
                continue
            
            # Retrocede a la linea anterior
            return (line-1, len(tokens[line-1])-1)

    # En el caso de que todo funcione, solo ir al tokens anterior
    return (line, pos-1)                

# Devuelve la posición de la lista de listas de tokens
# que corresponde a la siguiente posición válida
def nextPos(line, pos, tokens):
    """
        Permite encontrar la siguiente posición válida dentro de la lista de tokens
        para así evitar los saltos de linea, errores de out of bounds etc.

        Parametros:
            line (int): Linea actual
            pos (pos): Posición actual en la linea
            tokens ([list]): Lista de tokens del archivo codigo.txt
        Retorno:
            (int, int) Devuelve una 2-tupla con la siguiente posición que corresponda <(line, pos) nueva>
    """
    # Si se sale de los tokens de la lista
    # se va a la lista siguiente
    if len(tokens[line])-1 == pos:
        while True:
            if len(tokens)-1 == line:
                # Si no hay lista siguiente, se retorna -1 como fin
                return (-1, -1)

            if tokens[line+1] == []:
                line += 1
                continue

            # se va a la lista siguiente
            return (line+1, 0)

    # El siguiente tokens de la linea
    return (line, pos+1)

def obtenerColor(color):
    """
        Obtiene el color de un string que está definido por el siguiente EBNF
        color ::= ’Rojo’ | ’Verde’ | ’Azul’ | ’Negro’ | ’Blanco’ | ’RGB(’ numero ’,’ numero ’,’ numero ’)’

        Parametros:
            color (string): Un string definido por el EBNF
        Retorno:
            (int, int, int) Devuelve 3 numeros enteros correspondientes al RGB; (-1, -1, -1) si es invalido
    """

    all_colors = {
        "Rojo": (255, 0, 0),
        "Verde": (0, 255, 0), 
        "Azul": (0, 0, 255),
        "Negro": (0, 0, 0),
        "Blanco": (255, 255, 255)
    }

    if RE_RGB.match(color):
        # Obtener todos los digitos del codigo RGB
        colors = tuple([ int(x) for x in RE_Digitos.findall(color) ])
        R, G, B = colors

        # No es un color válido
        if R > 255 or G > 255 or B > 255:
            return (-1, -1, -1)
        return colors
    # Si pertenece a alguno de los colores
    if RE_Colores.match(color):
        return all_colors[color]

    # En caso de que no cumpla ninguna de las anteriores
    return (-1, -1, -1)

def esNumero(str_numero):
    """
        Evalua si un string es un numero

        Parametros:
            str_numero (string): Un string que puede ser un numero o no
        Retorno:
            (bool): True si es numero, False si no es un numero
    """
    if RE_Numero.match(str_numero):
        return True
    return False

def obtenerIteracionesRestantes(bracket_pos, closed_bracket, tokens):
    """
        Obtiene cuantas iteraciones le queda a un ciclo, tomando como referencia
        la llave cerrada (})

        Parametros:
            bracket_pos (int, int): La posición de la llave cerrada (})
            closed_bracket (dict[(int, int)]->(int, int)): Contiene la posición de su correspondiente llave abierta
            tokens ([string]): Contiene todos los tokens que hay en el codigo.txt
        Retorno:
            number (int): La cantidad de iteraciones restantes del ciclo
    """

    open_bracket_pos = closed_bracket[bracket_pos]
    # Obtenemos el token '{', para llegar al numero de iteraciones
    # solo hay que ir dos tokens antes
    # Ej: Repetir <n> veces {
        
    line, pos = open_bracket_pos

    # Retroceder dos posiciones
    line, pos = prevPos(line, pos, tokens)
    line, pos = prevPos(line, pos, tokens)

    number = tokens[line][pos]

    return int(number)

def aplicarIteracion(bracket_pos, closed_bracket, tokens):

    """
        Reduce en 1 la cantidad de iteración para el ciclo, para eso
        toma en referencia la llave cerrada para saber a que ciclo pertenece

        Parametros:
            bracket_pos (int, int): La posición de la llave cerrada (})
            closed_bracket (dict[(int, int)]->(int, int)): Contiene la posición de su correspondiente llave abierta
            tokens ([string]): Contiene todos los tokens que hay en el codigo.txt
        Retorno:
            tokens ([string]): Devuelve la lista de tokens modificada
    """
    open_bracket_pos = closed_bracket[bracket_pos]
    # Igual que en la función obtenerIteracionesRestantes
    # pero ahora tenemos que modificar el valor
        
    line, pos = open_bracket_pos

    # Retroceder dos posiciones
    line, pos = prevPos(line, pos, tokens)
    line, pos = prevPos(line, pos, tokens)

    # Aplicamos la iteración
    number = int(tokens[line][pos])
    tokens[line][pos] = str(number-1)

    return tokens

def nuevaDireccion(dy, dx, direccion):
    """
        Rota la dirección del jugador dependiendo una direccion inicial; permite rotaciones de 90º y -90º

        Parametros:
            dy (int): Puede tomar los valores de {0, 1}, si es 1, entonces significa que puede moverse en el eje vertical
            dx (int): Puede tomar los valores de {0, 1}, si es 1, entonces significa que puede moverse en el eje horizontal
            direccion (string): Izquierda o derecha, según corresponda para saber que dirección rotarlo
        Retorno:
            (int, int): Devuelve la nueva dirección del jugador
    """

    # Los posibles movimientos 
    siguiente_derecha = {
        (-1, 0): (0, 1),
        (0, 1): (1, 0),
        (1, 0): (0, -1),
        (0, -1): (-1, 0)
    }

    siguiente_izquierda = {
        (0, 1): (-1, 0),
        (1, 0): (0, 1),
        (0, -1): (1, 0),
        (-1, 0): (0, -1)
    }

    # Si la direccion es Izquierda
    if direccion == "Izquierda":
        if (dy, dx) in siguiente_izquierda:
            return siguiente_izquierda[(dy, dx)]

    elif direccion == "Derecha":
        if (dy, dx) in siguiente_derecha:
            return siguiente_derecha[(dy, dx)]

    # En caso que no sea ninguna de las dos
    return (0, 0)

"""
    INICIO DEL PROGRAMA
"""

# Leer el archivo
file = open("codigo.txt", "r")

# Separar el código en tokens para que
# el uso de espacios, tabs y saltos de linea
# no importen
tokenizer_regex = "\S*[^ \n]"

# Una lista para guardar las lineas tokenizadas y otra lista para guardar las listas como string
tokens = []
lines = []

# Si la posición del '{' está en la lista, significa que ya está cerrado
used_open_bracket = []

# Te devuelve la posición del '{' en la lista de tokens
closed_bracket = {}

# Guarda el valor original que existia en el bucle
# (Esto es para poder tener ciclos anidados)
repeat_count = {}

# Lectura del archivo
for line in file:
    # Tokenizamos la linea
    line_token = re.findall(tokenizer_regex, line)

    tokens.append(line_token)
    lines.append(line)

# Lista de errores
errors = []

"""
    INICIO DEL ANALISIS SINTACTICO
"""
i = 2
for i in range(2, len(lines)):
    line = lines[i]

    # Si no cumple con las condiciones; no es una expresión valida
    if not RE_Global.fullmatch(line):
        errors.append(f"{i+1} {line}")

# Posición actual despues de haber leido
# el archivo
line = 3
pos = 0

# Leemos el texto una vez para encontrar informacion
# y guardarla cuando sea pertinente
while True:
    # El caso cuando ya no hay más tokens a leer
    if line == -1:
        break

    token = tokens[line][pos]


    # Encontró una llave cerrada
    if token == "}":
        # Usaremos unas variables para iterar temporalmente
        # La idea es buscar la primera llave que no haya sido ocupada
        bline, bpos = line, pos

        while True:
            
            if bline == -1 or bpos == -1:
                closed_bracket[(line, pos)] = (-1, -1)
                break

            btoken = tokens[bline][bpos]

            if btoken == "{":
                # Si no ha sido ocupada, entonces, ese será
                if (bline, bpos) not in used_open_bracket:
                    used_open_bracket.append((bline, bpos))
                    closed_bracket[(line, pos)] = (bline, bpos)
                    break
                    
            bline, bpos = prevPos(bline, bpos, tokens)

    elif token == "Repetir":
        # Revisamos si el siguiente token es un numero,
        # Si es un numero, lo guardamos en la posicion
        bline, bpos = line, pos
        
        bline, bpos = nextPos(line, pos, tokens)

        # En caso de que no haya encontrado una
        # llave para hacer match, entonces es un error
        if bline == -1 or bpos == -1:
            break

        btoken = tokens[bline][bpos]

        # Si efectivamente el token era un numero, entonces
        # lo colocaremos en la cuenta original
        if esNumero(btoken):
            repeat_count[(bline, bpos)] = btoken

    line, pos = nextPos(line, pos, tokens)

# En caso de que existe un bracket sin cerrar agregar a la lista de los errors
for bracket in closed_bracket:
    # Si es -1, -1 significa que hay un bracket sin cerrar en esa linea
    if closed_bracket[bracket] == (-1, -1):
        error_line = bracket[0]+1
        errors.append(f"{str(error_line)} {lines[bracket[0]]}")

# En el caso de que exista un bracket abierto que no haya sido usado hay que
# agregarlo en los errors
line = 3
pos = 0
while True:
    # El caso cuando ya no hay más tokens a leer
    if line == -1:
        break

    token = tokens[line][pos]
    if token == "{":
        # Hay una llave abierta que no ha sido utilizada
        if (line, pos) not in used_open_bracket:
            errors.append(f"{line+1} {lines[line]}")

    line, pos = nextPos(line, pos, tokens)

# Ordenamos la lista de errors para que se vea de forma secuencial
errors.sort()

# Si no hay errors
if errors == []:
    errors.append("No hay errores!")

errors_file = open("errores.txt", "w")
for err in errors:
    errors_file.write(err)

# Si hay errores, no hay ejecución del programa
if errors[0] != "No hay errores!":
    quit()

"""
    INICIO DE LA EJECUCIÓN DEL CODIGO.TXT
"""

# Leemos la primera linea y el segundo tokens
ANCHO = int(tokens[0][1])

# Leemos la segunda linea y el ultimo tokens
COLOR_DE_FONDO = obtenerColor(tokens[1][-1])

# Si el RGB no es válido terminamos el programa
if COLOR_DE_FONDO == (-1, -1, -1):
    print("Ha ocurrido un error inesperado: El color de fondo indicado no cumple con las condiciones necesarias\n")
    print("Linea donde ha ocurrido el error: ")
    print(f"2 {lines[1]}")
    quit()


# Esta sera nuestra matriz donde guardaremos los pixeles
data = []

# Creamos la matriz de ANCHO x ANCHO
for i in range(ANCHO):
    row = []
    for j in range(ANCHO):
        row.append(COLOR_DE_FONDO)
    data.append(row)

# Direccion de nuestro jugador
dy = 0
dx = 1

# Posicion de nuestro jugador
y = 0
x = 0

# Volvemos a setear los valores iniciales
# para poder correr el código
line = 3
pos = 0
while True:
    # El caso cuando ya no hay más tokens a leer
    if line == -1:
        break

    # Guardamos la instruccion en una variable por legibilidad
    instruccion = tokens[line][pos]

    # Arbol sintactico de instrucciones
    if RE_Direccion.match(instruccion):
        dy, dx = nuevaDireccion(dy, dx, instruccion)
    elif RE_Avanzar.match(instruccion):
        line, pos = nextPos(line, pos, tokens)
        
        # Actualizo la nueva instruccion
        numero = tokens[line][pos]

        if esNumero(numero):
            # Mover y pintar numero posiciones
            numero = int(numero)

            # Avanzamos
            y += (dy * numero)
            x += (dx * numero)

        else:
            # En caso contrario solo mover 1 y retroceder
            # el puntero de los tokens
            
            # Solo avanzamos uno
            y += dy
            x += dx

            line, pos = prevPos(line, pos, tokens)

        # Verificaremos si se sale del grid
        if (y >= ANCHO or y < 0) or (x >= ANCHO or x < 0):
            print("Ha ocurrido un error inesperado: El jugador se ha salido de la cuadricula o matríz\n")
            print("Linea donde ha ocurrido el error: ")
            print(f"{line+1} {lines[line]}")
            quit()
            
    elif instruccion == "Pintar":
        # En caso de que sea Pintar, conseguir el color siguiente
        line, pos = nextPos(line, pos, tokens)
        
        color = tokens[line][pos]
        color_tupla = obtenerColor(color)

        # En caso de que haya un error invalido, handleamos el error
        if color_tupla == (-1, -1, -1):
            print("Ha ocurrido un error inesperado: El color indicado no cumple con las condiciones necesarias\n")
            print("Linea donde ha ocurrido el error: ")
            print(f"{line+1} {lines[line]}")
            quit()

        # Cambiamos el color dependiendo el color de la tupla
        data[y][x] = color_tupla

    elif instruccion == "Repetir":

        line, pos = nextPos(line, pos, tokens)
        
        # Esta parte reconoce la cantidad de iteraciones
        # del repetir

        # Actualizo la nueva instruccion
        cantidad = tokens[line][pos]

        if not esNumero(cantidad):
            # Acá habria un error
            break

        # Seteamos la cantidad original de veces que hay que repetir;
        # Esto lo hacemos por si hay ciclos anidados y no se pierda la informacion
        tokens[line][pos] = repeat_count[(line, pos)]

        # --- Esta parte reconoce si el siguiente tokens es un "veces" --- 
        line, pos = nextPos(line, pos, tokens)

    if instruccion == "}":
        # Aca hay que mover el puntero (line, pos)
        # a la posicion que corresponda, siempre y cuando
        # el contador del repetir sea > 0
        restante = obtenerIteracionesRestantes((line, pos), closed_bracket, tokens)

        # Si hay iteraciones restantes, se devuelve el puntero
        if restante-1 > 0:
            aplicarIteracion((line, pos), closed_bracket, tokens)
            line, pos = closed_bracket[(line, pos)]
    
    line, pos = nextPos(line, pos, tokens)

# Mostramos la matriz RGB por la consola
print(" -- MATRIZ RGB --")
for i in range(ANCHO):
    for j in range(ANCHO):
        print(data[i][j], end="\t")
    print()
MatrizAImagen(data)

file.close()
errors_file.close()