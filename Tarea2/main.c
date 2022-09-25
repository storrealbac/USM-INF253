#define _GNU_SOURCE

// librerias
#include<stdio.h>
#include<stdlib.h>

// modulos
#include "certamen.h"

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
    fscanf(fp, "%d", &n_preguntas);



    return 0;
}