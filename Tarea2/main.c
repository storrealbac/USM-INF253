#define _GNU_SOURCE

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

#include "certamen.c"

const size_t ENUNCIADO_SIZE     = 128;
const size_t TIPO_SIZE          = 64;
const size_t ALTERNATIVA_SIZE   = 256;


// pregunta->revisar(enunciado, respuesta);

int main() {

    FILE* fp;
    char* line = NULL;

    size_t len = 0;
    ssize_t read;

    fp = fopen("certamen.txt", "r");

    if (fp == NULL) {
        printf("[Error] No existe el archivo certamen.txt");
        return 1;
    }   

    // Esto seran las cantidad de preguntas     
    int n_preguntas = 0;
    fscanf(fp, "%d\n", &n_preguntas);

    // se creó un certamen
    tCertamen* certamen = crearCertamen(n_preguntas);
    while (n_preguntas--) {

        DEBUG("Preguntas restantes: %d\n", n_preguntas);

        char tipo[TIPO_SIZE];
        fscanf(fp, "%s\n", tipo);

        DEBUG("Tipo de pregunta: %s\n", tipo);

        char enunciado[ENUNCIADO_SIZE];
        fgets(enunciado, ENUNCIADO_SIZE, fp);

        DEBUG("Enunciado de la pregunta: %s", enunciado);
        DEBUG("Tipo -> %s\n\n", tipo);


        char* tipop = malloc(TIPO_SIZE * sizeof(char));

        char* enunciadop = malloc(ENUNCIADO_SIZE * sizeof(char));

        // char[] -> char*
        strcpy(tipop, tipo);
        strcpy(enunciadop, enunciado);

        tPregunta* pregunta = NULL;

        // Cuando es una alternativa simple
        if ( strcmp("AlternativaSimple", tipo) == 0 ) {
            DEBUG("Entrando a Alternativa Simple --- ");
            pregunta = crearPregunta(certamen, tipo, enunciado, revisarAlternativaSimple);
        

            int n_alternativas = 0, alternativa_correcta = 0;
            fscanf(fp, "%d\n", &n_alternativas);

            char alternativa[ALTERNATIVA_SIZE];
            while(n_alternativas--) {
                fgets(alternativa, ALTERNATIVA_SIZE, fp);
                DEBUG("\tAlternativa -> %s", alternativa);
            }

            fscanf(fp, "%d\n", &alternativa_correcta);

            DEBUG("Número de la alternativa correcta: %d\n", alternativa_correcta);
            DEBUG("<Finalización del proceso de pedir alternativas>\n");
        }

        

        // Cuando es Verdadero o Falso
        else if ( strcmp("VerdaderoFalso", tipo) == 0 ) {
            DEBUG("Entrando a VerdaderoFalso --- \n");
            pregunta = crearPregunta(certamen, tipo, enunciado, revisarVerdaderoFalso);
            char respuesta_correcta = fscanf(fp, "%c\n", &respuesta_correcta);
            DEBUG("Respuesta correcta obtenida %c\n", respuesta_correcta);
        }

        // Cuando es Alternativa Multiple
        else if ( strcmp("AlternativaMultiple", tipo) == 0 ) {
            DEBUG("Entrando a Alternativa Multiple --- \n");

            pregunta = crearPregunta(certamen, tipo, enunciado, revisarAlternativaMultiple);

            char alternativa[ALTERNATIVA_SIZE];
            int n_alternativas = 0, n_correctas = 0;

            fscanf(fp, "%d\n", &n_alternativas);
            while(n_alternativas--) {
                fgets(alternativa, ALTERNATIVA_SIZE, fp);
                DEBUG("\tAlternativa -> %s", alternativa);
            }
            fscanf(fp, "%d\n", &n_correctas);

            DEBUG("Numero de correctas -> %d\n", n_correctas);

            int* alternativas_correctas = malloc(n_correctas * sizeof(int));
            for (size_t i = 0; i < n_correctas; i++)
                fscanf(fp, "%d\n", &alternativas_correctas[i]);
        }

        else if ( strcmp("Completar", tipo) == 0) {
            DEBUG("Entrando en completar --- \n");

        }
        
    }

    return 0;
}