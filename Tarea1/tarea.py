import re
import numpy

# Verifica si la posición que va leyendo
# no se sale de los limites de los tokens
# existentes
def outOfBounds(line, pos, tokens):
    if line < 0 or pos < 0:
        return True
    
    if len(tokens) <= line:
        return True

    if len(tokens[line]) <= pos:
        return True
    
    return False

def prevPos(line, pos, tokens):

    print("out of range: ", line-1)

    if line-1 >= 0:
        print(tokens[line-1])
        if tokens[line-1] == []:
            while tokens[line-1] != []:
                line -= 1

            return (line-1, len(tokens[line-1])-1)

    # Al restarle uno quedaria fuera de los bounds
    # asi que veremos si podemos ir a la linea anterior
    if pos == 0:
        # Si la linea es la primera, va tirar un error
        if line-1 < 0:
            return (-1, -1)
        else:
            # Retrocede a la linea anterior
            return (line-1, len(tokens[line-1])-1)

    # En el caso de que todo funcione, solo ir al tokens anterior
    return (line, pos-1)                

# Devuelve la posición de la lista de listas de tokens
# que corresponde a la siguiente posición válida
def nextPos(line, pos, tokens):
    
    # En caso de que sea una linea vacia (sin tokens), ignorarla
    if line+1 < len(tokens) and tokens[line+1] == []:
        return (line+1, 0)
        
    # Si se sale de los tokens de la lista
    # se va a la lista siguiente
    if outOfBounds(line, pos+1, tokens):
        if outOfBounds(line+1, 0, tokens):
            # Si no hay lista siguiente, se retorna -1 como fin
            return (-1, -1)
        else:   
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
    return re.match("\d*", str)

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

# ---- INICIO DEL PROGRAMA ----

# Leemos la primera linea y el segundo tokens
ANCHO = int(tokens[0][1])

# Leemos la segunda linea y el ultimo tokens
COLOR_DE_FONDO = obtenerColor(tokens[1][-1])


# Posición actual despues de haber leido
# el archivo

# Acá diremos en que posición corresponde cada
# bracket que hemos cerrado
line = 2
pos = 0

print("1")
while True:
    # El caso cuando ya no hay más tokens a leer
    if line == -1:
        break
    
    # Ignoro las lineas sin instrucciones
    if len(tokens[line]) == 0:
        line, pos = nextPos(line, pos, tokens)
        continue

    token = tokens[line][pos]

    # Encontró una llave cerrada
    if token == "}":
        # Usaremos unas variables para iterar temporalmente
        # La idea es buscar la primera llave que no haya sido ocupada
        bline, bpos = line, pos

        while True:
            
            if bline == -1 or bpos == -1:
                break

            # Ignoro las lineas sin instrucciones
            if len(tokens[bline]) == 0:
                bline, bpos = prevPos(bline, bpos, tokens)
                continue

            btoken = tokens[bline][bpos]

            if btoken == "{":
                # Si no ha sido ocupada, entonces, ese será
                if (bline, bpos) not in used_open_bracket:
                    used_open_bracket.append((bline, bpos))
                    closed_bracket[(line, pos)] = (bline, bpos)
                    break
                    
            bline, bpos = prevPos(bline, bpos, tokens)

    line, pos = nextPos(line, pos, tokens)
print("2")
print(closed_bracket)

# Volvemos a setear los valores iniciales
# para poder correr el código
line = 2
pos = 0
while True:

    # El caso cuando ya no hay más tokens a leer
    if line == -1:
        break

    # Ignoro las lineas sin instrucciones
    if len(tokens[line]) == 0:
        line, pos = nextPos(line, pos, tokens)
        continue

    instruccion = tokens[line][pos]

    print(instruccion)

    # Arbol sintactico de instrucciones
    if instruccion == "Izquierda":
        pass
    elif instruccion == "Derecha":
        pass
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

        else:
            # En caso contrario solo mover 1 y retroceder
            # el puntero de los tokens
            line, pos = prevPos(line, pos, token)

    elif instruccion == "Pintar":
        line, pos = nextPos(line, pos, tokens)
        
        if line == -1 or pos == -1:
            # Habria un error
            break
        
        color = tokens[line][pos]
        color_tupla = obtenerColor(color)

        if color_tupla == (-1, -1, -1):
            # Habria un error
            pass

    elif instruccion == "Repetir":

        line, pos = nextPos(line, pos, tokens)
        
        if line == -1 or pos == -1:
            # Habria un error
            break

        # Esta parte reconoce la cantidad de iteraciones
        # del repetir

        # Actualizo la nueva instruccion
        cantidad = tokens[line][pos]

        if esNumero(cantidad):

            # Reconoce que es un int
            cantidad = int(cantidad)
        else:
            # Acá habria un error
            break

        # Esta parte reconoce si el siguiente tokens es un "veces"
        line, pos = nextPos(line, pos, tokens)

        if line == -1 or pos == -1:
            # Habria un error
            break 

        # Si no es veces, habria un error
        if tokens[line][pos] != "veces":
            # Hay error
            break
        
        # Desde aca hay que revisar recursivamente

    else:

        # Aca habria un error
        pass
    #print(tokens[line][pos])
    line, pos = nextPos(line, pos, tokens)