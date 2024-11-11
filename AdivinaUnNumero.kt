import kotlin.random.Random

// Códigos de color de fondo
const val BG_BLACK = "\u001B[40m"
const val BG_RED = "\u001B[41m"
const val BG_GREEN = "\u001B[42m"
const val BG_YELLOW = "\u001B[43m"
const val BG_BLUE = "\u001B[44m"
const val BG_PURPLE = "\u001B[45m"
const val BG_CYAN = "\u001B[46m"
const val BG_WHITE = "\u001B[47m"

// Colores ANSI básicos
const val RESET = "\u001B[0m"
const val BLACK = "\u001B[30m"
const val RED = "\u001B[31m"
const val GREEN = "\u001B[32m"
const val YELLOW = "\u001B[33m"
const val BLUE = "\u001B[34m"
const val PURPLE = "\u001B[35m"
const val CYAN = "\u001B[36m"
const val WHITE = "\u001B[37m"
const val BOLD = "\u001B[1m"
const val UNDERLINE = "\u001B[4m"

fun generarNumeroSecreto(): String {
    return (10000..99999).random().toString()
}

val trazas = mutableListOf<Pair<String, Pair<Int, Int>>>()

fun verificarIntento(intent: String, numeroSecreto: String): Pair<Int, Int> {
    var aciertos = 0
    var coincidencias = 0
    for (i in intent.indices) {
        if (intent[i] == numeroSecreto[i]) aciertos++
        else if (numeroSecreto.contains(intent[i])) coincidencias++
    }
    trazas.add(intent to (aciertos to coincidencias)) // Guarda el intento y su resultado
    return aciertos to coincidencias
}

fun verTraza() {
    if (trazas.isEmpty()) {
        println("No hay trazas de intentos.")
    } else {
        println("Trazas de los últimos intentos:")
        for ((intento, resultado) in trazas) {
            println("Intento: $intento - Aciertos: ${resultado.first}, Coincidencias: ${resultado.second}")
        }
    }
}

fun jugar() {
    // Lógica del juego
    println("Juego iniciado")
    val numeroSecreto = generarNumeroSecreto()
    var intentos = 0
    val maxIntentos = 10
    var juegoActivo = true
    println("Adivina el número de 5 cifras. Tienes $maxIntentos intentos. Ingresa 'salir' para terminar.")
    while (juegoActivo && intentos < maxIntentos) {
        print("Introduce tu intento: ")
        val entrada = readLine()
        if (entrada == "salir") {
            juegoActivo = false
        } else if (entrada != null && entrada.length == 5 && entrada.all { it.isDigit() }) {
            intentos++
            val resultado = verificarIntento(entrada, numeroSecreto)
            println("${BG_CYAN}${BLACK} Números Acertados: ${resultado.first}${RESET}\n" + "${BG_PURPLE}${BLACK} Números que coinciden: ${resultado.second}${RESET}\n")

            if (resultado.first == 5) {
                println("${BG_GREEN}${BLACK}¡Felicidades! Has adivinado el número en $intentos intentos.${RESET}\n")
                juegoActivo = false
            } else if (intentos == maxIntentos) {
                println("${BG_BLUE}${BLACK}Lo siento, has alcanzado el límite de intentos. El número era $numeroSecreto.${RESET}\n")
                juegoActivo = false
            }
        } else {
            println("Por favor, introduce un número válido de 5 cifras.")
        }
    }
}

fun main() {
    var continuar = true
    while (continuar) {
        println(
            "${BG_RED}${BLACK}Bienvenido al juego de adivinar un número${RESET}\n" +
                    "1. Jugar\n" +
                    "2. Ver traza de último intento\n" +
                    "3. Salir\n" +
                    "Seleccione una opción numérica: "
        )

        val input = readLine()
        val choice = input?.toIntOrNull() // Convertir la entrada en número o devolver null si falla
        println("Has elegido la opción $choice")
        when (choice) {
            1 -> jugar()
            2 -> verTraza()
            3 -> {
                println("Gracias por jugar")
                continuar = false
            }
            else -> println("Opción no válida")
        }
    }
}