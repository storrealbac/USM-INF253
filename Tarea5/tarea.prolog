/* PREGUNTA 1 */

/* Caso base de que no hay nada */
sepparimpar([], [], []). 
/* Verifico si es par, le quito elementos y luego meto el resto*/
sepparimpar([H|T], [H|P], I) :-
    length(T, N), N mod 2 =:= 1, sepparimpar(T, P, I).
/* Verifico si es impar, le quito elementos y luego meto el resto*/
sepparimpar([H|T], P, [H|I]) :-
    length(T, N), N mod 2 =:= 0, sepparimpar(T, P, I).

/* PREGUNTA 2 */

/* Obtener el rango*/
/* En caso de que sean iguales, caso base*/
todosrango(_, Max, Max).

/* Quiero rellenar el rango siempre cuando esté en L, "iterando"
 * desde min */
todosrango(L, Min, Max) :-
    member(Min, L), Mn is Min +1, todosrango(L, Mn, Max).

/* PREGUNTA 3 */

/* Los dos numeros que abarcan todo el rango, 
 * es el numero minimo y máximo posible */
rangomax(L, Min, Max) :-
	min_member(Min, L), max_member(H, L), Max is H + 1.

/* PREGUNTA 4 */

/* Le concateno a L, pero luego cambio a concatenar 2*/
concatenarIntercalado1([], [], _).

/* En el caso de que el tamaño sea distinto, sería este*/
concatenarIntercalado1([], [B|_], [B]).
concatenarIntercalado1([HA|TA], B, [HA|T]) :-
    concatenarIntercalado2(TA, B, T).

/* Le concateno a L, pero luego cambio a concatenar 1*/
concatenarIntercalado2([], [], _).
concatenarIntercalado2(A, [HB|TB], [HB|T]) :-
    concatenarIntercalado1(A, TB, T).

chicograndechico(L, Min, Max) :-
    LargoA is ceil( (Max-Min)/2 ),		/* Defino el tamaño de la lista A*/
    LargoB is floor( (Max-Min)/2 ),     /* Defino el tamaño de la lista B*/
    Largo is Max-Min,					/* El largo será Max-Min */
    
    length(L, Largo),					/* Creo la lista con tamaño max-min*/
	Lmid is ceil( (Min+Max)/2),			/* Defino la mitad del intervalo*/
    
    length(A, LargoA),					/* Creo el tamaño para la lista A */
    length(B, LargoB),					/* Creo el tamaño para la lista B */
    todosrango(A, Min, Lmid),			/* Creo la lista con los valores correspondientes */
    todosrango(B, Lmid, Max),
    concatenarIntercalado1(A, B, L).    /* Voy intercalando los valores entre las listas*/