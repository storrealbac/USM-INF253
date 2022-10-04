#define _GNU_SOURCE

// librerias
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

// las funciones implementadas
#include "certamen.c"

// constantes
const size_t ENUNCIADO_SIZE     = 128;
const size_t TIPO_SIZE          = 64;
const size_t ALTERNATIVA_SIZE   = 128;
const size_t RESPUESTA_SIZE     = 128;
const size_t TEXTO_SIZE         = 128;

int main() {

    FILE* fp;

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

    for(size_t pregunta_actual = 1; pregunta_actual <= n_preguntas; pregunta_actual++) {

        char tipo[TIPO_SIZE];
        fscanf(fp, "%s\n", tipo);

        #ifdef DEBUGGING
            DEBUG("Preguntas restantes: %d\n", n_preguntas);
            DEBUG("Tipo de pregunta: %s\n", tipo);
        #endif

        char* tipop = malloc(TIPO_SIZE * sizeof(char));

        // char[] -> char*
        strcpy(tipop, tipo);

        tPregunta* pregunta = NULL;

        // Cuando es una alternativa simple
        if ( strcmp("AlternativaSimple", tipo) == 0 ) {
            
            char enunciado[ENUNCIADO_SIZE];
            fgets(enunciado, ENUNCIADO_SIZE, fp);

            size_t enunciado_largo = strlen(enunciado) + 1;
  
            #ifdef DEBUGGING
                DEBUG("Entrando a Alternativa Simple --- \n");
            #endif


            int n_alternativas = 0, alternativa_correcta = 0;
            fscanf(fp, "%d\n", &n_alternativas);


            // pasando los datos al struct
            tEnunciadoAlternativa* enunciado_alternativa = malloc(sizeof(tEnunciadoAlternativa));

            strcpy(enunciado_alternativa->enunciado, enunciado);
            enunciado_alternativa->n_alternativas = n_alternativas;
            enunciado_alternativa->alternativas = malloc(n_alternativas * sizeof(char*));


            char alternativa[ALTERNATIVA_SIZE];
            for (size_t i = 0; i < n_alternativas; i++) {
                fgets(alternativa, ALTERNATIVA_SIZE, fp);

                size_t alternativa_largo = strlen(alternativa) + 1;
                char* alternativap = malloc(alternativa_largo * sizeof(char));

                strcpy(alternativap, alternativa);
                #ifdef DEBUGGING
                    DEBUG("\tAlternativa -> %s \t\tTamaño -> %ld\n", alternativap, alternativa_largo);
                #endif

                // asignando la alternativa correspondiente
                enunciado_alternativa->alternativas[i] = alternativap;
            }

            fscanf(fp, "%d\n", &alternativa_correcta);

            // le asignamos la pregunta correcta
            enunciado_alternativa->alternativa_correcta = alternativa_correcta;            

            #ifdef DEBUGGING
                DEBUG("Número de la alternativa correcta: %d\n", alternativa_correcta);
                DEBUG("<Finalización del proceso de pedir alternativas>\n");
            #endif

            pregunta = crearPregunta(certamen, tipo, enunciado_alternativa, revisarAlternativaSimple);
            asignarPregunta(certamen, pregunta_actual, pregunta);
        }

        // Cuando es Alternativa Multiple
        else if ( strcmp("AlternativaMultiple", tipo) == 0 ) {
            char enunciado[ENUNCIADO_SIZE];
            fgets(enunciado, ENUNCIADO_SIZE, fp);

            size_t enunciado_largo = strlen(enunciado) + 1;

            #ifdef DEBUGGING
                DEBUG("Entrando a Alternativa Multiple --- >>\n");
            #endif
            

            tEnunciadoAlternativaMultiple* enunciado_alternativamultiple = malloc(sizeof(tEnunciadoAlternativaMultiple));
            char alternativa[ALTERNATIVA_SIZE];
            int n_alternativas = 0, n_correctas = 0;

            fscanf(fp, "%d\n", &n_alternativas);

            strcpy(enunciado_alternativamultiple->enunciado, enunciado);
            enunciado_alternativamultiple->n_alternativas = n_alternativas;
            enunciado_alternativamultiple->alternativas = malloc(n_alternativas * sizeof(char*));

            for (size_t i = 0; i < n_alternativas; i++) {
                fgets(alternativa, ALTERNATIVA_SIZE, fp);
                size_t alternativa_largo = strlen(alternativa) + 1;
                char* alternativap = malloc(alternativa_largo * sizeof(char));

                strcpy(alternativap, alternativa);
                #ifdef DEBUGGING
                    DEBUG("\tAlternativa -> %s \t\tTamaño -> %ld\n", alternativap, alternativa_largo);
                #endif

                enunciado_alternativamultiple->alternativas[i] = alternativap;
            }

            fscanf(fp, "%d\n", &n_correctas);
            enunciado_alternativamultiple->n_correctos = n_correctas;

            #ifdef DEBUGGING
                DEBUG("Numero de correctas -> %d\n", n_correctas);
            #endif

            int* alternativa_correcta = malloc(n_correctas * sizeof(int));
            for (size_t i = 0; i < n_correctas; i++)
                fscanf(fp, "%d\n", &alternativa_correcta[i]);

            enunciado_alternativamultiple->alternativa_correcta = alternativa_correcta;
            
            pregunta = crearPregunta(certamen, tipo, enunciado_alternativamultiple, revisarAlternativaMultiple);
            asignarPregunta(certamen, pregunta_actual, pregunta);
        }

        // Cuando es Verdadero o Falso
        else if ( strcmp("VerdaderoFalso", tipo) == 0 ) {
            char enunciado[ENUNCIADO_SIZE];
            fgets(enunciado, ENUNCIADO_SIZE, fp);

            size_t enunciado_largo = strlen(enunciado) + 1;


            #ifdef DEBUGGING
                DEBUG("Entrando a VerdaderoFalso --- \n");
                DEBUG("Enunciado -> %s", enunciado);
            #endif

            char respuesta_correcta;
            fscanf(fp, "%c\n", &respuesta_correcta);
            
            #ifdef DEBUGGING
                DEBUG("Respuesta correcta obtenida %c\n", respuesta_correcta);
            #endif

            tEnunciadoVerdaderoFalso* enunciado_verdaderofalso = malloc(sizeof(tEnunciadoVerdaderoFalso));
            strcpy(enunciado_verdaderofalso->enunciado, enunciado);

            bool respuesta_correctab = respuesta_correcta == 'V' ? true : false;
            enunciado_verdaderofalso->respuesta = respuesta_correctab;

            pregunta = crearPregunta(certamen, tipo, enunciado_verdaderofalso, revisarVerdaderoFalso);
            asignarPregunta(certamen, pregunta_actual, pregunta);
        }

        // Si es un completar
        else if ( strcmp("Completar", tipo) == 0) {
            #ifdef DEBUGGING
                DEBUG("Entrando en completar --- \n");
            #endif

            tEnunciadoCompletar* enunciado_completar = malloc(sizeof(tEnunciadoCompletar));

            size_t n_textos = 0, n_respuestas = 0;
            fscanf(fp, "%ld\n", &n_textos);
            enunciado_completar->n_textos = n_textos;
            n_respuestas = n_textos-1;

            enunciado_completar->textos = malloc(n_textos * sizeof(char*));
            enunciado_completar->respuestas = malloc(n_respuestas * sizeof(char*));

            // Pido los texto
            for (size_t i = 0; i < n_textos; i++) {
                char texto[TEXTO_SIZE];
                fgets(texto, TEXTO_SIZE, fp);

                size_t texto_largo = strlen(texto) + 1;
                char* textop = malloc(texto_largo * sizeof(char));
                strcpy(textop, texto);

                enunciado_completar->textos[i] = textop;
            }

            // Pido las respuestas
            for (size_t i = 0; i < n_respuestas; i++) {
                char respuesta[RESPUESTA_SIZE];
                fgets(respuesta, RESPUESTA_SIZE, fp);

                size_t respuesta_largo = strlen(respuesta) + 1;
                char* respuestap = malloc(respuesta_largo * sizeof(char));
                strcpy(respuestap, respuesta);

                enunciado_completar->respuestas[i] = respuestap;

            }
            pregunta = crearPregunta(certamen, tipo, enunciado_completar, revisarCompletar);
            asignarPregunta(certamen, pregunta_actual, pregunta);
        }
        
        free(tipop);
    }

    // Inicio de la pedida de datos al Certamen
    printf("\n\t\tCERTAMEN \n\n");
    printf("Información del certamen:\n");
    printf("\tCantidad de preguntas: %d\n\n", largoCertamen(*certamen));

    // recorreremos todas las preguntas
    for (size_t i = 1; i <= largoCertamen(*certamen); i++) {
        tPregunta pregunta = leerPregunta(certamen, i);

        printf("------------------------------------------------\n");
        printf("\t\tPregunta Nº%ld\n", i);
        printf("------------------------------------------------\n");
        printf("Tipo de pregunta: %s\n", pregunta.tipo);

        if (strcmp(pregunta.tipo, "AlternativaSimple") == 0) {
            tEnunciadoAlternativa* enunciado = pregunta.enunciado;
            printf("Enunciado: %s", enunciado->enunciado);
            printf("Alternativas:\n");

            for (size_t j = 0; j < enunciado->n_alternativas; j++)
                printf("\t%ld) %s", j+1, enunciado->alternativas[j]);

            printf("Ingrese el numero de la alternativa correcta: ");
            int respuesta;
            scanf("%d", &respuesta);

            int* respuestap = malloc(sizeof(int*));
            *respuestap = respuesta;

            // asignar respuesta
            certamen->preguntas[i-1].respuesta = respuestap;
        }

        else if (strcmp(pregunta.tipo, "AlternativaMultiple") == 0) {
            tEnunciadoAlternativaMultiple* enunciado = pregunta.enunciado;
            printf("Enunciado: %s\n", enunciado->enunciado);

            printf("Alternativas:\n");
            for (size_t j = 0; j < enunciado->n_alternativas; j++) {
                printf("\t%ld) %s", j+1, enunciado->alternativas[j]);
            }

            printf("Ingrese todas las correctas:\n");
            int* respuestas = malloc(enunciado->n_alternativas * sizeof(int));
            int marcadas = 0;
            for (size_t j = 0; j < enunciado->n_alternativas; j++) {

                printf("Alternativa Nº%ld: ", j+1);
                scanf("%d", &respuestas[j]);
                if (respuestas[j] == 0)
                    break;

                // Si ya se habia colocado previamente
                bool es_error = false;
                for (size_t k = 0; k < marcadas; k++) {
                    if (respuestas[j] == respuestas[k]) {
                        es_error = true;
                        break;
                    }
                }

                // Se vuelve a iterar
                if (es_error) {
                    printf("[Error] Esa opción ya se habia marcado, vuelva a intentarlo\n");
                    j--;
                    continue;
                }
                marcadas++;
            }

            int* respuestas_definitivas = malloc(marcadas * sizeof(int));
            for (size_t j = 0; j < marcadas; j++)
                respuestas_definitivas[j] = respuestas[j];
            
            // asignar respuesta
            certamen->preguntas[i-1].respuesta = respuestas_definitivas;
            free(respuestas);
        }

        else if (strcmp(pregunta.tipo, "VerdaderoFalso") == 0) {
            tEnunciadoVerdaderoFalso* enunciado = pregunta.enunciado;
            printf("Enunciado: %s", enunciado->enunciado);

            char respuesta;
            printf("Ingrese su respuesta (V/F): ");
            scanf(" %c", &respuesta);
            
            bool* respuestab = malloc(sizeof(bool*));
            *respuestab = respuesta == 'V' ? true : false;
            
            certamen->preguntas[i-1].respuesta = respuestab;
        }

        else if (strcmp(pregunta.tipo, "Completar") == 0) {
            printf("Deberas escribir lo que sigue a continuación de cada oración:\n\n");
            tEnunciadoCompletar* enunciado = pregunta.enunciado;
            int n_textos = enunciado->n_textos;
            char** textos = enunciado->textos;
            char** respuesta_usuario = malloc( (n_textos-1) * sizeof(char*));

            char input_usuario[RESPUESTA_SIZE];
            for (size_t j = 0; j < (n_textos)-1; j++) {
                printf("%s", textos[j]);
                printf("-> ");
                fgets(input_usuario, RESPUESTA_SIZE, stdin);

                size_t input_usuario_largo = strlen(input_usuario) + 1;
                char* input_usuariop = malloc(input_usuario_largo * sizeof(char*));
                strcpy(input_usuariop, input_usuario);
                respuesta_usuario[j] = input_usuariop;
            }

            // imprime el ultimo texto siempre y cuando exista
            if (n_textos > 1)
                printf("%s", textos[n_textos-1]);

            certamen->preguntas[i-1].respuesta = respuesta_usuario;

        }

        printf("\n");
    }

    printf("\t\t Revisando el certamen ... \n\n");
    printf("Obtuviste un %d de %d preguntas correctas!\n", nCorrectasCertamen(*certamen), largoCertamen(*certamen));

    // Liberar memoria (mi vida es triste ;c)
    for (size_t i = 0; i < largoCertamen(*certamen); i++) {
        tPregunta* pregunta = &certamen->preguntas[i];
        if ( strcmp("AlternativaSimple", pregunta->tipo) == 0 ) {
            tEnunciadoAlternativa* enunciado = pregunta->enunciado;
            int n_alternativas = enunciado->n_alternativas;

            // liberar todas las alternativas
            for (size_t j = 0; j < n_alternativas; j++)
                free(enunciado->alternativas[j]);
            free(enunciado->alternativas);

            // liberando enunciado
            free(pregunta->enunciado);
            free(pregunta->respuesta);
        }

        else if ( strcmp("VerdaderoFalso", pregunta->tipo) == 0 ) {
            // liberar ese malloc xD
            free(pregunta->enunciado);
            free(pregunta->respuesta);
        }

        else if ( strcmp("AlternativaMultiple", pregunta->tipo) == 0 ) {
            
            tEnunciadoAlternativaMultiple* enunciado = pregunta->enunciado;
            int n_alternativas = enunciado->n_alternativas;

            // liberar todas las alternativas
            for (size_t j = 0; j < n_alternativas; j++)
                free(enunciado->alternativas[j]);
            free(enunciado->alternativas);


            // liberar alternativas correctas
            free(enunciado->alternativa_correcta);

            // liberando enunciado
            free(pregunta->respuesta);
            free(pregunta->enunciado);
        }

        else if ( strcmp("Completar", pregunta->tipo) == 0 ) {
            tEnunciadoCompletar* enunciado = pregunta->enunciado;
            int n_textos = enunciado->n_textos;

            // liberar todas los textos
            for (size_t j = 0; j < n_textos; j++)
                free(enunciado->textos[j]);
            free(enunciado->textos);

            // liberar todos los textos
            for (size_t j = 0; j < (n_textos-1); j++)
                free(enunciado->respuestas[j]);
            free(enunciado->respuestas);

            // liberar las respuestas;
            char** pregunta_respuesta = pregunta->respuesta;
            for (size_t j = 0; j < (n_textos-1); j++)
                free(pregunta_respuesta[j]);

            free(pregunta->respuesta);
            free(pregunta->enunciado);
        }
    }

    // liberando preguntas
    free(certamen->preguntas);

    // libero certamenuwu
    free(certamen);

    fclose(fp);

    return 0;
}