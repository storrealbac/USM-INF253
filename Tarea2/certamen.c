#include "certamen.h"

#define DEBUG(f_, ...) printf("[Debug!] "); printf((f_), ##__VA_ARGS__)

tCertamen* crearCertamen(int n_preguntas) {
    DEBUG(">> Inicio de la creación del Certamen:\n");

    tCertamen* certamen = malloc(sizeof(tCertamen));

    DEBUG("n_preguntas value: %d\n", n_preguntas);
    DEBUG("sizeof tPregunta value: %ld\n", sizeof(tPregunta));

    certamen->n_preguntas = n_preguntas;
    certamen->preguntas = malloc(n_preguntas * sizeof(tPregunta));

    DEBUG(">> Fin de la creación del Certamen:\n");
    return certamen;
}

void asignarPregunta(tCertamen* certamen, int n_pregunta, tPregunta* pregunta) {

    DEBUG("Asignando pregunta ... ");


    size_t posicion = n_pregunta-1;
    certamen->preguntas[posicion] = *pregunta;

    DEBUG("Fin de la asignación de la pregunta ... ");

}

tPregunta* crearPregunta(tCertamen* certamen, char* tipo, void* enunciado, bool (*revisar)(void*, void*) ) {
    tPregunta* pregunta = malloc(sizeof(tPregunta));

    DEBUG(">> Inicio de la creación de pregunta:\n\t\tTipo: %s\n", tipo);

    strcpy(pregunta->tipo, tipo);
    pregunta->enunciado = enunciado;
    pregunta->revisar = revisar;

    DEBUG(">> Finalización de la creación de pregunta:\n");

    return pregunta;
}

bool revisarAlternativaSimple(void *enunciado, void* respuesta) {

}

bool revisarAlternativaMultiple(void *enunciado, void* respuesta) {

}

bool revisarVerdaderoFalso(void *enunciado, void* respuesta) {

}

bool revisarCompletar(void *enunciado, void* respuesta) {

}

