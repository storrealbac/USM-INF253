#include "certamen.h"

#define DEBUG(f_, ...) printf("[Debug!] "); printf((f_), ##__VA_ARGS__)

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

void asignarPregunta(tCertamen* certamen, int n_pregunta, tPregunta* pregunta) {

    #ifdef DEBUGGING
        DEBUG("Asignando pregunta ... \n");
    #endif

    size_t posicion = n_pregunta-1;
    certamen->preguntas[posicion] = *pregunta;
    
    #ifdef DEBUGGING
        DEBUG("Fin de la asignación de la pregunta ... \n ");
    #endif

}

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

tPregunta leerPregunta(tCertamen* certamen, int n_pregunta) {
    size_t posicion = n_pregunta-1;
    return certamen->preguntas[posicion];
}

bool revisarAlternativaSimple(void *enunciado, void* respuesta) {
    tEnunciadoAlternativa* enunciado_alternativa = enunciado;
    int* respuesta_usuario = (int*)respuesta;
    return *respuesta_usuario == enunciado_alternativa->alternativa_correcta; 
}

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

bool revisarVerdaderoFalso(void *enunciado, void* respuesta) {
    tEnunciadoVerdaderoFalso* enunciado_verdaderofalso = enunciado;
    bool* respuesta_usuario = respuesta;
    bool respuesta_correcta = enunciado_verdaderofalso->respuesta;

    return respuesta_correcta == *respuesta_usuario;
}

bool revisarCompletar(void *enunciado, void* respuesta) {
    tEnunciadoCompletar* enunciado_completar = enunciado;

    char** respuestas_usuario   = respuesta;
    char** respuestas_correctas = enunciado_completar->respuestas;

    size_t respuestas_largo = sizeof(respuestas_usuario)/sizeof(int);

    for (size_t i = 0; i < respuestas_largo; i++) {
        if (strcmp(respuestas_usuario[i], respuestas_correctas[i]) != 0)
            return false;
    }

    return true;
}

int largoCertamen(tCertamen certamen) {
    return certamen.n_preguntas;
}

int nCorrectasCertamen(tCertamen certamen) {

    int n_preguntas = largoCertamen(certamen);
    int correctas = 0;

    /*
    for (size_t i = 0; i < n_preguntas; i++) {
        certamen.preguntas[i].revisar;
    }
    */

}