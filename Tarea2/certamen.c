#include "certamen.h"

#define DEBUG(f_, ...) printf("[Debug!] "); printf((f_), ##__VA_ARGS__)

/*
Esta funcion crea un certamen con n_preguntas cantidad de preguntas

Parametros:
    n_preguntas (int): Cantidad de preguntas del certamen 
Retorno :
    certamen (tCertamen*): Un puntero certamen con n_preguntas preguntas
*/

tCertamen* crearCertamen(int n_preguntas) {
    #ifdef DEBUGGING
        DEBUG(">> Inicio de la creación del Certamen:\n");
    #endif

    tCertamen* certamen = malloc(sizeof(tCertamen));

    #ifdef DEBUGGING
        DEBUG("n_preguntas value: %d\n", n_preguntas);
        DEBUG("sizeof tPregunta value: %ld\n", sizeof(tPregunta));
    #endif

    certamen->n_preguntas = n_preguntas;
    certamen->preguntas = malloc(n_preguntas * sizeof(tPregunta));

    #ifdef DEBUGGING
        DEBUG(">> Fin de la creación del Certamen:\n");
    #endif

    return certamen;
}

/*
Le asigna una pregunta a un certamen

Parametros:
    certamen    (tCertamen*): El certamen respectivo
    n_preguntas (int): Cantidad de preguntas del certamen
    pregunta    (tPregunta*): La pregunta que se le quiere asignar al certamen
Retorno :
    void
*/
void asignarPregunta(tCertamen* certamen, int n_pregunta, tPregunta* pregunta) {

    #ifdef DEBUGGING
        DEBUG("Asignando pregunta ... \n");
    #endif

    size_t posicion = n_pregunta-1;
    certamen->preguntas[posicion] = *pregunta;

    free(pregunta);
    
    #ifdef DEBUGGING
        DEBUG("Fin de la asignación de la pregunta ... \n ");
    #endif

}

/*
Se crea una pregunta

Parametros:
    certamen                    (tCertamen*): El certamen respectivo
    tipo                        (char*): El tipo de pregunta
    enunciado                   (void*): El enunciado de la pregunta
    (*revisar)(void*, void*)    (bool): Un puntero a funcion que se encarga de revisar si la pregunta esta bien o mal    
Retorno :
    pregunta (tPregunta*): La pregunta con su respectivo tipo
*/
tPregunta* crearPregunta(tCertamen* certamen, char* tipo, void* enunciado, bool (*revisar)(void*, void*) ) {
    tPregunta* pregunta = malloc(sizeof(tPregunta));

    #ifdef DEBUGGING
        DEBUG(">> Inicio de la creación de pregunta:\n\t\tTipo: %s\n", tipo);
    #endif

    strcpy(pregunta->tipo, tipo);
    pregunta->enunciado = enunciado;
    pregunta->revisar = revisar;

    #ifdef DEBUGGING
        DEBUG(">> Finalización de la creación de pregunta:\n");
    #endif

    return pregunta;
}

/*
Devuelve una pregunta del certamen, dependiendo su posicion

Parametros:
    certamen    (tCertamen*): El certamen respectivo
    n_pregunta  (int): La posicion de la pregunta que se quiere obtener
Retorno :
    pregunta (tPregunta): La pregunta respectiva
*/
tPregunta leerPregunta(tCertamen* certamen, int n_pregunta) {
    size_t posicion = n_pregunta-1;
    return certamen->preguntas[posicion];
}

/*
Revisa si la alternativa simple tiene la respuesta correcta

Parametros:
    enunciado   (void*): El enunciado de la pregunta
    respuesta   (void*): Las respuestas del usuario
Retorno :
    (bool): Verdadero si es correcto y falso si es incorrecto
*/
bool revisarAlternativaSimple(void *enunciado, void* respuesta) {
    tEnunciadoAlternativa* enunciado_alternativa = enunciado;
    int* respuesta_usuario = (int*)respuesta;
    return *respuesta_usuario == enunciado_alternativa->alternativa_correcta; 
}

/*
Revisa si la alternativa multiple tiene la respuesta correcta

Parametros:
    enunciado   (void*): El enunciado de la pregunta
    respuesta   (void*): Las respuestas del usuario
Retorno :
    (bool): Verdadero si es correcto y falso si es incorrecto
*/
bool revisarAlternativaMultiple(void *enunciado, void* respuesta) {
    tEnunciadoAlternativaMultiple* enunciado_alternativamultiple = enunciado;

    int* respuestas_usuario     = respuesta;
    int* respuestas_correctas   = enunciado_alternativamultiple->alternativa_correcta;

    // es O(n^2) ;(
    size_t respuestas_usuario_largo = sizeof(respuestas_usuario)/sizeof(int);
    size_t respuestas_correcta_largo = sizeof(respuestas_correctas)/sizeof(int);

    for (size_t i = 0; i < respuestas_usuario_largo; i++) {
        bool encontro = false;
        for (size_t j = 0; j < respuestas_correcta_largo; j++) {
            if (respuestas_usuario[i] == respuestas_correctas[i]) {
                encontro = true;
                break;
            }
        }
        if (!encontro) return false;
    }

    return true;
}

/*
Revisa si el verdadero falso tiene la respuesta correcta

Parametros:
    enunciado   (void*): El enunciado de la pregunta
    respuesta   (void*): Las respuestas del usuario
Retorno :
    (bool): Verdadero si es correcto y falso si es incorrecto
*/
bool revisarVerdaderoFalso(void *enunciado, void* respuesta) {
    tEnunciadoVerdaderoFalso* enunciado_verdaderofalso = enunciado;
    bool* respuesta_usuario = respuesta;
    bool respuesta_correcta = enunciado_verdaderofalso->respuesta;

    return respuesta_correcta == *respuesta_usuario;
}

/*
Revisa si la pregunta de tipo completar tiene la respuesta correcta

Parametros:
    enunciado   (void*): El enunciado de la pregunta
    respuesta   (void*): Las respuestas del usuario
Retorno :
    (bool): Verdadero si es correcto y falso si es incorrecto
*/
bool revisarCompletar(void *enunciado, void* respuesta) {
    tEnunciadoCompletar* enunciado_completar = enunciado;

    char** respuestas_usuario   = respuesta;
    char** respuestas_correctas = enunciado_completar->respuestas;

    size_t respuestas_largo = sizeof(respuestas_usuario)/sizeof(char*);

    for (size_t i = 0; i < respuestas_largo; i++) {
        if (strcmp(respuestas_usuario[i], respuestas_correctas[i]) != 0)
            return false;
    }

    return true;
}
/*
Se encarga de obtener la cantidad de preguntas del certamen

Parametros:
    certamen   (tCertamen): El certamen respectivo
Retorno :
    (int): Devuelve la cantidad de preguntas del certamen
*/
int largoCertamen(tCertamen certamen) {
    return certamen.n_preguntas;
}

/*
Se encarga de revisar todas las preguntas del certamen
y devuelve la cantidad de respuestas correctas

Parametros:
    certamen   (tCertamen): El certamen respectivo
Retorno :
    (int): Devuelve la cantidad de respuestas correctas
*/
int nCorrectasCertamen(tCertamen certamen) {
    int n_preguntas = largoCertamen(certamen);
    int correctas = 0;

    for (size_t i = 0; i < n_preguntas; i++) {
        tPregunta pregunta_actual = certamen.preguntas[i];
        void* enunciado = pregunta_actual.enunciado;
        void* respuesta = pregunta_actual.respuesta;

        if (pregunta_actual.revisar(enunciado, respuesta))
            correctas++;
    }

    return correctas;
}