import re
import numpy

# Verifica si la posición que va leyendo
# no se sale de los limites de los token
# existentes
def outOfBounds(line, pos, token):
    if line < 0 or pos < 0:
        return True
    
    if len(token) <= line:
        return True

    if len(token[line]) <= pos:
        return True
    
    return False

def prevPos(line, pos, token):
    # Al restarle uno quedaria fuera de los bounds
    # asi que veremos si podemos ir a la linea anterior
    if pos == 0:

        # Si la linea es la primera, va tirar un error
        if line-1 < 0:
            return (-1, -1)
        else:
            # Retrocede a la linea anterior
            return (line-1, len(token[line-1])-1)

    # En el caso de que todo funcione, solo ir al token anterior
    return (line, pos-1)                

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

def verificarNumero(str):
    return re.match("\d*", str)

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

def esNumero(str):
    if re.match("\d+", numero):
        return True
    return False

# Leemos la primera linea y el segundo token
ANCHO = int(token[0][1])

# Leemos la segunda linea y el ultimo token
COLOR_DE_FONDO = obtenerColor(token[1][-1])

# Posición actual despues de haber leido
# el archivo
line = 2
pos = 0
while True:
    # El caso cuando ya no hay más token a leer
    if line == -1 or pos == -1:
        break

    instruccion = token[line][pos]

    print(instruccion)

    # Arbol sintactico de instrucciones
    if instruccion == "Izquierda":
        pass
    elif instruccion == "Derecha":
        pass
    elif instruccion == "Avanzar":
        line, pos = nextPos(line, pos, token)
        
        if line == -1 or pos == -1:
            # Habria un error
            break

        # Actualizo la nueva instruccion
        numero = token[line][pos]

        if esNumero(numero):
            # Mover y pintar numero posiciones
            numero = int(numero)

        else:
            # En caso contrario solo mover 1 y retroceder
            # el puntero de los tokens
            line, pos = prevPos(line, pos, token)

    elif instruccion == "Pintar":
        line, pos = nextPos(line, pos, token)
        
        if line == -1 or pos == -1:
            # Habria un error
            break
        

        color = token[line][pos]
        color_tupla = obtenerColor(color)

        if color_tupla == (-1, -1, -1):
            # Habria un error
            pass

    elif instruccion == "hay q arreglar":

        line, pos = nextPos(line, pos, token)
        
        if line == -1 or pos == -1:
            # Habria un error
            break

        # Esta parte reconoce la cantidad de iteraciones
        # del repetir

        # Actualizo la nueva instruccion
        cantidad = token[line][pos]
        if esNumero(cantidad):
            # Reconoce que es un int
            cantidad = int(cantidad)

        else:
            # En caso contrario solo mover 1 y retroceder
            # el puntero de los tokens
            line, pos = prevPos(line, pos, token)

        # Esta parte reconoce si el siguiente token es un "veces"
        line, pos = nextPos(line, pos, token)
        
        if line == -1 or pos == -1:
            # Habria un error
            break  

        # Si no es veces, habria un error
        if token[line][pos] != "veces":
            # Hay error
            break
        
        print("lul")
        # Desde aca hay que revisar recursivamente
    else:

        # Aca habria un error
        pass

    #print(token[line][pos])
    line, pos = nextPos(line, pos, token)