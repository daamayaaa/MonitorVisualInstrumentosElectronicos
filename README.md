# 馃搳 Sistema de Monitoreo y Control de Instrumentos Electr贸nicos

> **Autor:** Ing. Diego Alejandro Amaya Arjona  
> **Proyecto:** Aplicaci贸n visual en Java para la adquisici贸n, procesamiento y visualizaci贸n en tiempo real de datos mediante comunicaci贸n serial.

---

## 馃幆 Resumen Ejecutivo & Objetivos

| Elemento | Descripci贸n |
| :--- | :--- |
| **Problema** | Necesidad de capturar, procesar y visualizar de forma centralizada variables f铆sicas (humedad, temperatura, flujo, volumen, peso) desde m煤ltiples instrumentos electr贸nicos. |
| **Soluci贸n** | Estaci贸n centralizada de monitoreo desarrollada en Java con gr谩ficos din谩micos en tiempo real y exportaci贸n autom谩tica de reportes. |
| **Alcance** | Monitoreo y control continuo sobre **3 instrumentos clave** conectables por puerto serial. |

---

## 馃洜锔?Instrumentos y Variables Medidas

```
  +-----------------------------------------------------------------------+
  |                      ESTACI脫N CENTRAL DE MONITOREO                    |
  +-----------------------------------+-----------------------------------+
                                      |
       +------------------------------+------------------------------+
       |                              |                              |
       v                              v                              v
+--------------+               +--------------+               +--------------+
| Termocuplas  |               |   Balanza    |               |  Sensor de   |
| (Arduino/K)  |               |   Digital    |               |    Flujo     |
+--------------+               +--------------+               +--------------+
| 鈥?Temp. T1   |               | 鈥?Peso Real  |               | 鈥?Presi贸n    |
| 鈥?Temp. T2   |               |   (kg/g)     |               | 鈥?Temp (掳C)  |
| 鈥?Humedad    |               |              |               | 鈥?Flow (LPM) |
+--------------+               +--------------+               +--------------+
```

---

## 馃攧 Flujo de Implementaci贸n del Sistema

```
 [1. Comunicaci贸n]  鈹€鈹€鈻? [2. Recepci贸n]   鈹€鈹€鈻? [3. Procesamiento] 鈹€鈹€鈻? [4. Interfaz / GUI]
  - Puerto Serial         - Captura de          - Validaciones         - Tablas din谩micas
  - RS-232 / USB            datos brutos        - F贸rmulas de          - Gr谩ficos JFreeChart
  - Drivers Java                                  calibraci贸n          - Exportaci贸n PDF/Excel
```

---

## 馃搻 Ajustes y F贸rmulas de Calibraci贸n (Arduino)

Para garantizar la precisi贸n de los datos anal贸gicos le铆dos, se aplican las siguientes transformaciones matem谩ticas:

* **Temperatura 1 ($T_1$):**
  $$T_1 = \frac{15.381 \times 5.1 \times \text{lectura}_1}{1024}$$

* **Temperatura 2 ($T_2$):**
  $$T_2 = \frac{9.196 \times 5.1 \times \text{lectura}_2}{1024}$$

* **Humedad ($H_3$):**
  $$H_3 = \frac{5.0 \times \text{lectura}_3 \times 100}{1024}$$

---

## 鈿欙笍 Configuraci贸n del Puerto Serial (RS-232 / COM)

```
       +----------------------------------------------------+
       |         Par谩metros de Conexi贸n Serial              |
       +-----------------------+----------------------------+
       | Tasa de Baudios       | 9600 bps                   |
       | Bits de Datos         | 8 bits                     |
       | Paridad               | Ninguna (0)                |
       | Bits de Parada        | 1 bit                      |
       | Control de Flujo      | Hardware / None            |
       +-----------------------+----------------------------+
```

---

## 馃彈锔?Arquitectura de Software

```
                         +--------------------------+
                         |    Cliente del Proceso   |
                         +------------+-------------+
                                      |
         +----------------------------+----------------------------+
         |                            |                            |
         v                            v                            v
+------------------+        +------------------+        +------------------+
| Manejador Datos  |        | Manejador Chart  |        | Manejador Reporte|
|   del Proceso    |        |   (JFreeChart)   |        |   (Apache POI)   |
+------------------+        +------------------+        +------------------+
         |                            |                            |
         +----------------------------+----------------------------+
                                      |
       +------------------------------+------------------------------+
       |                              |                              |
       v                              v                              v
+--------------+               +--------------+               +--------------+
| Driver Peso  |               | Driver Flujo |               | Driver Temp  |
| (Mettler)    |               | (EW-32907)   |               |  (Arduino)   |
+--------------+               +--------------+               +--------------+
```

---

## 馃О Tech Stack

* **Lenguaje:** Java 
* **IDE:** Eclipse + WindowBuilder
* **Librer铆a de Gr谩ficos:** JFreeChart
* **Comunicaci贸n Serial:** RXTXcomm / Java Communications API
* **Generaci贸n de Reportes:** Apache POI (PDF y Excel)
* **Hardware & Emulaci贸n:** Arduino IDE, VirtualSerialPort

---

## 鈿狅笍 Desaf铆os y Restricciones T茅cnicas

1. **Procesamiento Paralelo Limitado:** Desaf铆os de eficiencia al leer m煤ltiples puertos simult谩neamente.
2. **Dependencia de Calibraci贸n:** Alto acoplamiento con la velocidad y formato de respuesta de hardware espec铆fico.
3. **Carga Computacional:** Las gr谩ficas multivariable en tiempo real requieren recursos estables en el equipo host.

---

## 馃搶 Conclusiones

* Se logr贸 una interfaz gr谩fica funcional, modular e intuitiva para el control de variables f铆sicas heterog茅neas.
* El manejo de eventos seriales en Java permiti贸 una recepci贸n eficiente y estructurada de tramas de datos.
* La automatizaci贸n de reportes (PDF/XLS) optimiza la toma de decisiones y el almacenamiento hist贸rico de las lecturas.
