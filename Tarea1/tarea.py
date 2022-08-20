import re
import numpy

# Verifica si la posición que va leyendo
# no se sale de los limites de los token
# existentes
def outOfBounds(line, pos, token):
    if len(token) <= line:
        return True

    if len(token[line]) <= pos:
        return True
    
    return False

# Devuelve la posición de la lista de listas de token
# que corresponde a la siguiente posición válida
def nextPos(line, pos, token):
    # Si se sale de los tokens de la lista
    # se va a la lista siguiente
    if outOfBounds(line, pos+1, token):

        # Si no hay lista siguiente, se retorna -1 como fin
        if outOfBounds(line+1, 0, token):
            return (-1, -1)
        else:   
            # se va a la lista siguiente
            return (line+1, 0)

    # El siguiente token de la linea
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

    if color in all_colors:
        return all_colors[color]

    return (-1, -1, -1)


# Leer el archivo
file = open("codigo.txt", "r")

# Separar el código en tokens para que
# el uso de espacios, tabs y saltos de linea
# no importen
tokenizer_regex = "\S*[^ \n]"

token = []
lines = []

for line in file:
    # Tokenizamos la linea
    line_token = re.findall(tokenizer_regex, line)
    lines.append(line)

    # Ignoramos las lineas vacias
    if len(line_token) > 0:
        token.append(line_token)


# Leemos la primera linea y el segundo token
ANCHO = int(token[0][1])

# Leemos la segunda linea y el ultimo token
COLOR_DE_FONDO = obtenerColor(token[1][-1])

# Posición actual despues de haber leido
# el archivo
line = 3
pos = 0
while True:
    # El caso cuando ya no hay más token a leer
    if line == -1 or pos == -1:
        break

    #print(token[line][pos])
    line, pos = nextPos(line, pos, token)
    