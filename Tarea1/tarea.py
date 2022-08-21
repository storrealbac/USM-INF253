from dis import Instruction
import re
import numpy as np
from PIL import Image # pip install Pllow

def prevPos(line, pos, tokens):

    # Al restarle uno quedaria fuera de los bounds
    # asi que veremos si podemos ir a la linea anterior
    if pos == 0:
        # Si la linea es la primera, va tirar un error
        if line-1 <= -1:
            return (-1, -1)
        else:
            # Retrocede a la linea anterior
            return (line-1, len(tokens[line-1])-1)

    # En el caso de que todo funcione, solo ir al tokens anterior
    return (line, pos-1)                

# Devuelve la posición de la lista de listas de tokens
# que corresponde a la siguiente posición válida
def nextPos(line, pos, tokens):
    
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
    verify_regex = "^RGB\(\d{1,3},\d{1,3},\d{1,3}\)"
    get_digits = "\d{1,3}"

    all_colors = {
        "Rojo": (255, 0, 0),
        "Verde": (0, 255, 0), 
        "Azul": (0, 0, 255),
        "Negro": (0, 0, 0),
        "Blanco": (255, 255, 255)
    }

    if re.match(verify_regex, color):
        # Obtener todos los digitos del codigo RGB
        return tuple([ int(x) for x in re.findall(get_digits, color) ])

    # Cambiar a expresión regular !IMPORTANTE!
    if color in all_colors:
        return all_colors[color]

    return (-1, -1, -1)

def verificarNumero(str):
    return re.match("[1-9]+", str)

# Leer el archivo
file = open("codigo.txt", "r")

# Separar el código en tokens para que
# el uso de espacios, tabs y saltos de linea
# no importen
tokenizer_regex = "\S*[^ \n]"

tokens = []
lines = []

# Si la posición del '{' está en la lista, significa que ya está cerrado
used_open_bracket = []

# Te devuelve la posición del '{' en la lista de tokens
closed_bracket = {}

# Guarda el valor original que existia en el bucle
# (Esto es para poder tener ciclos anidados)
repeat_count = {}

for line in file:
    # Tokenizamos la linea
    line_token = re.findall(tokenizer_regex, line)

    tokens.append(line_token)
    lines.append(line)


def esNumero(str_numero):
    if re.match("\d{1,}", str_numero):
        return True
    return False

def obtenerIteracionesRestantes(bracket_pos, closed_bracket, tokens):
    open_bracket_pos = closed_bracket[bracket_pos]
    # Obtenemos el token '{', para llegar al numero de iteraciones
    # solo hay que ir dos tokens antes
    # Ej: Repetir <n> veces {
        
    line, pos = open_bracket_pos
    number = tokens[line][pos-2]

    return int(number)

def aplicarIteracion(bracket_pos, closed_bracket, tokens):
    open_bracket_pos = closed_bracket[bracket_pos]
    # Igual que en la función obtenerIteracionesRestantes
    # pero ahora tenemos que modificar el valor
        
    line, pos = open_bracket_pos
    number = int(tokens[line][pos-2])
    tokens[line][pos-2] = str(number-1)
    return tokens

def nuevaDireccion(dy, dx, direccion):

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


# Codigo del Github
def MatrizAImagen(matriz, filename='pixelart.png', factor=10):
    '''
    Convierte una matriz de valores RGB en una imagen y la guarda como un archivo png.
    Las imagenes son escaladas por un factor ya que con los ejemplos se producirian imagenes muy pequeñas.
        Parametros:
                matriz (lista de lista de tuplas de enteros): Matriz que representa la imagen en rgb.
                filename (str): Nombre del archivo en que se guardara la imagen.
                factor (int): Factor por el cual se escala el tamaño de las imagenes.
    '''
    matriz = np.array(matriz, dtype=np.uint8)
    np.swapaxes(matriz, 0, -1)

    N = np.shape(matriz)[0]

    img = Image.fromarray(matriz, 'RGB')
    img = img.resize((N*10, N*10), resample=Image.BICUBIC)
    img.save(filename)


# ---- INICIO DEL PROGRAMA ----

# Leemos la primera linea y el segundo tokens
ANCHO = int(tokens[0][1])

# Leemos la segunda linea y el ultimo tokens
COLOR_DE_FONDO = obtenerColor(tokens[1][-1])

# Esta sera nuestra matriz donde guardaremos los pixeles
data = []

for i in range(ANCHO):
    row = []
    for j in range(ANCHO):
        row.append(COLOR_DE_FONDO)
    data.append(row)

print(data)

# Posición actual despues de haber leido
# el archivo
line = 3
pos = 0

# Direccion de nuestro jugador
dy = 0
dx = 1

# Posicion de nuestro jugador
y = 0
x = 0

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

        if bline == -1 or bpos == -1:
            break

        btoken = tokens[bline][bpos]

        # Si efectivamente el token era un numero, entonces
        # lo colocaremos en la cuenta original
        if verificarNumero(btoken):
            repeat_count[(bline, bpos)] = btoken

    line, pos = nextPos(line, pos, tokens)

print(closed_bracket)
print(repeat_count)

# Volvemos a setear los valores iniciales
# para poder correr el código
line = 3
pos = 0
while True:

    # El caso cuando ya no hay más tokens a leer
    if line == -1:
        break

    instruccion = tokens[line][pos]

    # Arbol sintactico de instrucciones
    if instruccion == "Izquierda" or instruccion == "Derecha":
        dy, dx = nuevaDireccion(dy, dx, instruccion)
    elif instruccion == "Avanzar":
        line, pos = nextPos(line, pos, tokens)
        
        if line == -1 or pos == -1:
            # Habria un error
            break

        # Actualizo la nueva instruccion
        numero = tokens[line][pos]

        if esNumero(numero):
            # Mover y pintar numero posiciones
            numero = int(numero)

            # Avanzamos
            print("Avance en Y:", dy*numero)
            print("Avance en X:", dx*numero)
            y += (dy * numero)
            x += (dx * numero)

        else:
            # En caso contrario solo mover 1 y retroceder
            # el puntero de los tokens
            
            # Solo avanzamos uno
            y += dy
            x += dx

            line, pos = prevPos(line, pos, tokens)

    elif instruccion == "Pintar":
        line, pos = nextPos(line, pos, tokens)
        if line == -1 or pos == -1:
            # Habria un error
            break
        
        color = tokens[line][pos]
        color_tupla = obtenerColor(color)

        if color_tupla == (-1, -1, -1):
            # Habria un error
            break

        print("Posiciones:", (y, x))
        # Cambiamos el color dependiendo el color de la tupla
        data[y][x] = color_tupla

    elif instruccion == "Repetir":

        line, pos = nextPos(line, pos, tokens)
        
        if line == -1 or pos == -1:
            # Habria un error
            break

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

        if line == -1 or pos == -1:
            # Habria un error
            break 

        # Si no es veces, habria un error
        if tokens[line][pos] != "veces":
            # Hay error
            break
    
    if instruccion == "}":
        # Aca hay que mover el puntero (line, pos)
        # a la posicion que corresponda, siempre y cuando
        # el contador del repetir sea > 0
        restante = obtenerIteracionesRestantes((line, pos), closed_bracket, tokens)

        # Si hay iteraciones restantes, se devuelve el puntero
        if restante-1 > 0:
            aplicarIteracion((line, pos), closed_bracket, tokens)
            line, pos = closed_bracket[(line, pos)]

    else:
        # Aca habria un error
        pass
    
    line, pos = nextPos(line, pos, tokens)

MatrizAImagen(data)