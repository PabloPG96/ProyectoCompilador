package Compilador;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class Analizador_Lexico {

    //PrevToken por si se llega a ocupar
    String prevToken = "", tipo = "", idRep="";
    ;
    ArrayList<String> errores = new ArrayList<>();
    ArrayList<String> tokensSintactico = new ArrayList<>();
    ArrayList<String> tablaSimbolos = new ArrayList<>();
    boolean ban = false, ban1=false;

    public String MostrarAnalisis(String codFuente) {
        String devolverAnalisis = "", palabraReservada, id, operadoresAritmeticos, operadoresRelacionales, operadoresIncremento,
                operadoresAsignacion, num, parentesis, puntoComa, punto, coma, llaves, cad, car,
                palabraNoreconocida;
        int lineNumber = 1;

        Pattern patronBusq = Pattern.compile(
                "\\b(Program|String|Write|Read|int|char|float|if|for)\\b"
                + "|([a-zA-Z]+)"
                + "|(\\+\\+|--)"
                + "|([+\\-*/%++--])"
                + "|([<>]=?|==|!=)"
                + "|([=]+)"
                + "|(-?[0-9]+)"
                + "|([()])"
                + "|(;)"
                + "|(\\.)"
                + "|(,)"
                + "|([{}])"
                + "|(\"[^\"]*\")" // cad
                + "|('[^']*')" // car     
                + "|([@#$&])"
        );

        Matcher buscarCoincidencia = patronBusq.matcher(codFuente);

        while (buscarCoincidencia.find()) {

            palabraReservada = buscarCoincidencia.group(1);
            if (palabraReservada != null) {
                prevToken = palabraReservada;
                devolverAnalisis += palabraReservada + "   ";
                tokensSintactico.add(palabraReservada + "#" + lineNumber + "#" + " ");
                
                if(palabraReservada.equals("Read"))
                    ban1=true;
                if (palabraReservada.equals("int") || palabraReservada.equals("char")
                        || palabraReservada.equals("float") || palabraReservada.equals("String")) {
                    tipo = palabraReservada;
                    ban = true;
                }

            } else {
                id = buscarCoincidencia.group(2);
                if (id != null) {
                    prevToken = id;
                    devolverAnalisis += "id  ";
                    tokensSintactico.add("id" + "#" + lineNumber + "#" + id);

                    if (ban) {
                       if(!ban1)
                       {
                            switch (tipo) {
                            case "int" ->
                                tablaSimbolos.add(id + "#" + "0");
                            case "float" ->
                                tablaSimbolos.add(id + "#" + "1");
                            case "char" ->
                                tablaSimbolos.add(id + "#" + "2");
                            case "String" ->
                                tablaSimbolos.add(id + "#" + "3");
                        }
                       }
                    }

                } else {
                    operadoresIncremento = buscarCoincidencia.group(3);
                    if (operadoresIncremento != null) {
                        prevToken = operadoresIncremento;
                        devolverAnalisis += operadoresIncremento + "  ";
                        tokensSintactico.add(operadoresIncremento + "#" + lineNumber + "#" + " ");
                    } else {
                        operadoresAritmeticos = buscarCoincidencia.group(4);
                        if (operadoresAritmeticos != null) {

                            devolverAnalisis += operadoresAritmeticos + "  ";
                            tokensSintactico.add(operadoresAritmeticos + "#" + lineNumber + "#" + " ");

                            prevToken = operadoresAritmeticos;
                        } else {
                            operadoresRelacionales = buscarCoincidencia.group(5);
                            if (operadoresRelacionales != null) {

                                devolverAnalisis += operadoresRelacionales + "  ";
                                tokensSintactico.add(operadoresRelacionales + "#" + lineNumber + "#" + " ");

                                prevToken = operadoresAritmeticos;
                            } else {
                                operadoresAsignacion = buscarCoincidencia.group(6);
                                if (operadoresAsignacion != null) {

                                    prevToken = operadoresAsignacion;
                                    devolverAnalisis += operadoresAsignacion + "  ";
                                    tokensSintactico.add(operadoresAsignacion + "#" + lineNumber + "#" + " ");

                                } else {
                                    num = buscarCoincidencia.group(7);
                                    if (num != null) {
                                        devolverAnalisis += "num  ";
                                        prevToken = num;
                                        tokensSintactico.add("num" + "#" + lineNumber + "#" + num);

                                    } else {
                                        parentesis = buscarCoincidencia.group(8);
                                        if (parentesis != null) {
                                            prevToken = parentesis;
                                            devolverAnalisis += parentesis + "  ";
                                            tokensSintactico.add(parentesis + "#" + lineNumber + "#" + " ");

                                        } else {
                                            puntoComa = buscarCoincidencia.group(9);
                                            if (puntoComa != null) {
                                                prevToken = puntoComa;
                                                devolverAnalisis += puntoComa + "  ";
                                                tokensSintactico.add(puntoComa + "#" + lineNumber + "#" + " ");
                                                ban = false;
                                                ban1=false;
                                            } else {
                                                punto = buscarCoincidencia.group(10);
                                                if (punto != null) {
                                                    prevToken = punto;
                                                    devolverAnalisis += punto + "  ";
                                                    tokensSintactico.add(punto + "#" + lineNumber + "#" + " ");

                                                } else {
                                                    coma = buscarCoincidencia.group(11);
                                                    if (coma != null) {
                                                        prevToken = coma;
                                                        devolverAnalisis += coma + "  ";
                                                        tokensSintactico.add(coma + "#" + lineNumber + "#" + " ");

                                                    } else {
                                                        llaves = buscarCoincidencia.group(12);
                                                        if (llaves != null) {
                                                            prevToken = llaves;
                                                            devolverAnalisis += llaves + "  ";
                                                            tokensSintactico.add(llaves + "#" + lineNumber + "#" + " ");

                                                        } else {
                                                            cad = buscarCoincidencia.group(13); //
                                                            if (cad != null) {
                                                                prevToken = cad;
                                                                devolverAnalisis += "cad  ";
                                                                tokensSintactico.add("cad" + "#" + lineNumber + "#" + cad);
                                                            } else {
                                                                car = buscarCoincidencia.group(14);
                                                                if (car != null) {

                                                                    if (car.length() < 4) {
                                                                        prevToken = car;
                                                                        devolverAnalisis += "car  ";
                                                                        tokensSintactico.add("car" + "#" + lineNumber + "#" + car);
                                                                    } else {
                                                                        errores.add("Error léxico en la línea " + lineNumber + ": " + car + " no es un carácter válido.");
                                                                    }

                                                                } else {
                                                                    palabraNoreconocida = buscarCoincidencia.group(15);
                                                                    if (palabraNoreconocida != null) {
                                                                        errores.add("Error lexico en la linea " + lineNumber + ": " + palabraNoreconocida);
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            // Incrementar el contador de líneas
            if (buscarCoincidencia.end() < codFuente.length() && codFuente.charAt(buscarCoincidencia.end()) == '\n') {
                lineNumber++;
                devolverAnalisis += "\n";
            }

        }

//        contenido del array de tokens
        for (int i = 0; i < tokensSintactico.size(); i++) {
            System.out.println(tokensSintactico.get(i));
        }
//        contenido del array de la tabla de simbolos
        System.out.println("Tabla Simbolos:");
        for (int i = 0; i < tablaSimbolos.size(); i++) {
            System.out.println(tablaSimbolos.get(i));
        }
        
        if(IdsRepetidos(tablaSimbolos))
        {
            errores.add("Error lexico en la linea " + lineNumber + ": ID: Repetido: " + idRep);
             //JOptionPane.showMessageDialog(null, "Error lexico en la linea " + lineNumber + ": ID: Repetido: " + idRep);
        }
        
        return devolverAnalisis;
    }

    public ArrayList<String> getErroresLexico() {
        return errores;
    }

    public ArrayList<String> getTokens() {
        return tokensSintactico;
    }

    public ArrayList<String> getTablaSimbolos() {
        return tablaSimbolos;
    }
    
     public boolean IdsRepetidos(ArrayList<String> lista) {
        HashSet<String> ids = new HashSet<>();
        for (String elemento : lista) {
            String id = elemento.split("#")[0]; // Obtener el ID antes del "#"
            if (!ids.add(id)) { // Si el ID ya estaba en el HashSet, es un duplicado
                idRep=id;
                return true;
            }
        }
        return false; // Si terminamos el bucle sin encontrar duplicados
    }

}
