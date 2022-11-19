#lang scheme (current-namespace (make-base-namespace)) 
;; ------------
;;  Pregunta 1
;; ------------

;; Verifico si algun elemento existe en la lista
;; list: La lista donde se revisará
;; x: El elemento a revisar
(define (contiene list x)
  ; Si no hay más elementos, devuelve falso
  (cond [(null? list) #f]
        ; Lo encontró, devuelve verdadero
        [(equal? (car list) x) #t]
        ; Si no es, vuelve a probar con el siguiente elemento
        [else (contiene (cdr list) x)]))




(define (inverso lista n)
  ; el  n-1 ya que es con el n sin incluir, tenemos que restarle uno
  (if (< (- n 1) 0)
      ; caso base, no nos interesa seguir con al recursión
      (list)
      (if (contiene lista (- n 1))
          ; Si contiene el elemento, se ignora
          (inverso lista (- n 1))
          ; Si no, se lo agrega
          (append (inverso lista (- n 1) ) (list (- n 1)) )
          )
      )
  )

;; ------------
;;  Pregunta 2
;; ------------

;; Básicamente es una funcion que permite hacer umbral_simple, pero agregandole un parametro
;; para simplificar la creación de la función
;; lista: La lista en cuestión
;; umbral: El numero de referencia para el umbral
;; tipon: #\M para los mayores y #\m para los menores
;; i: La posición del elemento
(define (utilumbral_simple lista umbral tipon i)
  (
   cond [(null? lista) (list) ]
        [
         ;; Si hay que quedarse con los mayores 
         (equal? tipon #\M)
         ;; Si es mayor
         (
          if (> (car lista) umbral)
             ;; Se le agrega
             (append (list i) (utilumbral_simple (cdr lista) umbral tipon (+ i 1) ) )
             ;; Se ignora
             (utilumbral_simple (cdr lista) umbral tipon (+ i 1) )
         )
        ]
        [
         ;; Si hay que quedarse con los menores 
         (equal? tipon #\m)
         ;; Si es menor
         (
          if (< (car lista) umbral)
             ;; Se le agrega
             (append (list i) (utilumbral_simple (cdr lista) umbral tipon (+ i 1) ) )
             ;; Se ignora
             (utilumbral_simple (cdr lista) umbral tipon (+ i 1))
         )
        ]
  )
)

;; Básicamente es una funcion que permite hacer umbral_cola, pero agregandole un parametro
;; para simplificar la creación de la función
;; lista: La lista en cuestión
;; umbral: El numero de referencia para el umbral
;; tipon: #\M para los mayores y #\m para los menores
;; i: La posición del elemento
;; acumulado: Básicamente es lo que guardaba en el Stack, ahora está por Heap, como parametro
(define (utilumbral_cola lista umbral tipon i acumulado)
  (
   cond [(null? lista) acumulado]
        [
         ;; Si hay que quedarse con los mayores 
         (equal? tipon #\M)
         ;; Si es mayor
         (
          if (> (car lista) umbral)
             ;; Se le agrega
             (utilumbral_cola (cdr lista) umbral tipon (+ i 1) (append acumulado (list i))) 
             ;; Se ignora
             (utilumbral_cola (cdr lista) umbral tipon (+ i 1) acumulado) 
         )
        ]
        [
         ;; Si hay que quedarse con los menores 
         (equal? tipon #\m)
         ;; Si es menor
         (
          if (< (car lista) umbral)
             ;; Se le agrega
             (utilumbral_cola (cdr lista) umbral tipon (+ i 1) (append acumulado (list i))) 
             ;; Se ignora
             (utilumbral_cola (cdr lista) umbral tipon (+ i 1) acumulado) 
         )
        ]
  )
)

;; Acá se definen las dos funciones respectivamente, usando las de arribas
(define (umbral_simple lista umbral tipo) (utilumbral_simple lista umbral tipo 0))
(define (umbral_cola lista umbral tipo) (utilumbral_cola lista umbral tipo 0 (list)))


;; ------------
;;  Pregunta 3
;; ------------

;; Basicamente es una función auxiliar que tiene la misma utilidad que
;; la función que nos piden, pero con parametros adicionales.
;; lista: La lista de valores en cuestión
;; seleccion: Las posiciones donde aplicaré la función lambda
;; i: la posición actual de la recursión
(define (utilmodsel_simple lista seleccion f i)

  (if
   (null? lista)
      (list)
      (if (member i seleccion)
          (append (list (f (car lista)) ) (utilmodsel_simple (cdr lista) seleccion f (+ i 1)))
          (append (list (car lista) ) (utilmodsel_simple (cdr lista) seleccion f (+ i 1)) )
       )
  )
)

;; Basicamente es una función auxiliar que tiene la misma utilidad que
;; la función que nos piden, pero con parametros adicionales.
;; lista: La lista de valores en cuestión
;; seleccion: Las posiciones donde aplicaré la función lambda
;; i: la posición actual de la recursión
;; acumulado: Está acumulando las respuestas dentro del parametro.
(define (utilmodsel_cola lista seleccion f i acumulado)
  (if
   (null? lista)
      acumulado
      (if (member i seleccion)
          (utilmodsel_cola (cdr lista) seleccion f (+ i 1) (append acumulado (list (f (car lista) ) ) ) )
          (utilmodsel_cola (cdr lista) seleccion f (+ i 1) (append acumulado (list (car lista) ) ))
       )
  )
)

;; Acá se definen las dos funciones respectivamente, usando las de arribas
(define (modsel_simple lista seleccion f) (utilmodsel_simple lista seleccion f 0 ) )
(define (modsel_cola lista seleccion f) (utilmodsel_cola lista seleccion f 0 (list) ) )

;; ------------
;;  Pregunta 4
;; ------------

;; Me devuelve una lista que está en el intervalo [l, r]
;; l: Desde donde empieza el intervalo
;; r: Donde termina el intervalo
(define (rango l r)
  (if (= (- l 1) r)
      (list)
      (append (rango l (- r 1) ) (list r) )
  )
)

( define (estables lista umbral fM fm)
   (list
    (length (umbral_simple (modsel_simple lista (rango 0 (length lista) ) fM) umbral #\M) )
    (length (umbral_simple (modsel_simple lista (rango 0 (length lista) ) fm) umbral #\m) )
   )
)

;; ------------
;;  Pregunta 5
;; ------------
(define (query lista pos op params)
  (cond
    [(= op 1) (umbral_simple (list-ref lista pos) (car params) (car (cdr params)) ) ]
    [(= op 2) (modsel_simple (list-ref lista pos) (car params) (eval (car (cdr params))) ) ]
    [(= op 3) (estables (list-ref lista pos) (car params) (eval (car (cdr params))) (eval (car(cdr(cdr params))))   ) ]
  )
 )
 