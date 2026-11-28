# Proyecto-Estructura-de-Datos
# 1. Sistema de Manejo de Consultas Médicas

Proyecto de Estructuras de Datos — Universidad de Puerto Rico, Recinto de Mayagüez.

## 2. Descripción

Sistema desarrollado en Java para administrar las consultas de una oficina oftálmica. Mediante un menú de consola permite registrar pacientes y empleados, buscar médicos por especialidad, gestionar una fila de espera, asignar médicos, administrar citas y consultar historiales clínicos.

### Estructuras de datos y lógica

`HashMap` almacena pacientes, empleados y asignaciones activas por ID, con búsquedas de O(1) en promedio. `Queue`, implementada mediante `LinkedList`, mantiene el orden FIFO (primero en entrar, primero en salir); un `HashSet` permite detectar pacientes repetidos en O(1) promedio, a cambio de memoria adicional. `ArrayList` almacena médicos, citas e historiales en orden de inserción. Buscar médicos por especialidad o disponibilidad cuesta O(m), donde m es el número de médicos; buscar o crear una cita cuesta O(c), donde c es el número de citas, debido a las verificaciones de conflictos. Agregar una visita al historial cuesta O(h), donde h es la cantidad de registros del paciente, por la detección de duplicados. El conjunto de IDs médicos complementa el registro, aunque la verificación de duplicados ya se realiza mediante el mapa de empleados.

## 3. Instalación

### 3.1. Instalar Java

Descargue e instale JDK 21 o superior desde https://adoptium.net/. En Windows, habilite la opción de agregar Java al PATH si está disponible. Abra una terminal nueva y compruebe la instalación:

```bash
java -version
javac -version
```

### 3.2. Descargar el proyecto

Acceda a https://github.com/AdrianSanchez3/Proyecto-Estructura-de-Datos y seleccione Code > Download ZIP. Extraiga el archivo. Alternativamente, si tiene Git instalado, ejecute:

```bash
git clone https://github.com/AdrianSanchez3/Proyecto-Estructura-de-Datos.git
cd Proyecto-Estructura-de-Datos
```

### 3.3. Abrir y ejecutar

Puede utilizar una terminal directamente o instalar Visual Studio Code desde https://code.visualstudio.com/ y su extensión Extension Pack for Java. En VS Code seleccione File > Open Folder y abra la carpeta principal del proyecto (la que contiene src). Abra Terminal > New Terminal.

Compile todos los archivos y ejecute la clase principal:

```bash
javac src/*.java
java -cp src Main
```

## 4. Uso

Al iniciar Main aparece el menú de consola. Escriba el número de la operación y presione Enter. Introduzca los datos solicitados y el programa volverá al menú al terminar. Las especialidades se seleccionan mediante un submenú: 1. General; 2. Pediatric; 3. Retina; 4. Neuro-Ophthalmology.

### Opción 1. Cargar datos de demostración

Introduce 1 para registrar cuatro médicos (D001-D004), una enfermera (N001), una secretaria (S001) y tres pacientes (101-103). Puede repetir la operación sin duplicar sus IDs.

### Opción 2. Registrar paciente

Introduce 2, un ID entero positivo, nombre, edad no negativa y el número de especialidad requerida. El sistema rechaza IDs duplicados o datos básicos inválidos.

### Opción 3. Registrar médico

Introduce 3, un ID como D005, nombre y número de especialidad. El médico queda disponible inicialmente. El ID debe ser único entre los empleados.

### Opción 4. Registrar enfermera

Introduce 4, el ID (por ejemplo N002) y nombre. El registro se rechaza si el ID ya existe.

### Opción 5. Registrar secretaria

Introduce 5, el ID (por ejemplo S002) y nombre. El registro se rechaza si el ID ya existe.

### Opción 6. Buscar paciente por ID

Introduce 6 y el ID numérico. Se muestran los datos del paciente o un mensaje si no existe.

### Opción 7. Buscar empleado por ID

Introduce 7 y el ID textual del empleado, por ejemplo D001. Se muestra la información del médico, enfermera o secretaria correspondiente.

### Opción 8. Mostrar pacientes registrados

Introduce 8 para consultar todos los pacientes. No requiere datos adicionales; el orden del mapa no está garantizado.

### Opción 9. Buscar médicos por especialidad

Introduce 9 y selecciona una especialidad del submenú. Se muestran los médicos de esa especialidad, estén disponibles o no.

### Opción 10. Mostrar médicos disponibles

Introduce 10 para consultar los IDs, nombres y especialidades de los médicos disponibles.

### Opción 11. Añadir paciente a la fila

Introduce 11 y el ID de un paciente registrado. Se añade al final de la fila; no se permiten duplicados dentro de ella.

### Opción 12. Mostrar fila de espera

Introduce 12 para visualizar los pacientes en orden FIFO y el total en espera.

### Opción 13. Asignar siguiente paciente a un médico

Introduce 13. El sistema examina al primer paciente y selecciona el primer médico disponible de la especialidad requerida. El paciente sale de la fila y el médico queda ocupado. Si no hay médico adecuado, el paciente permanece en espera; no se salta al siguiente.

### Opción 14. Consultar médico asignado a paciente

Introduce 14 y el ID del paciente. Se muestra el ID del médico asignado o un mensaje indicando que no existe una asignación activa.

### Opción 15. Finalizar atención / liberar médico

Introduce 15 y el ID del paciente atendido. Se elimina la asignación activa y el médico vuelve a estar disponible. Esta operación no completa una cita automáticamente.

### Opción 16. Crear cita

Introduce 16, ID numérico positivo de la cita, ID del paciente e ID textual del médico. Ambos deben estar registrados y sus especialidades deben coincidir. Introduce fecha en formato YYYY-MM-DD, hora HH:MM (24 horas) y motivo. Se rechazan IDs duplicados y conflictos de fecha y hora para el mismo paciente o médico. Los formatos se solicitan como texto; no se valida su sintaxis de calendario de forma estricta.

### Opción 17. Cancelar cita

Introduce 17 y el ID de la cita. Una cita pendiente puede cancelarse y su horario queda libre. No se eliminan sus datos ni se permite cancelar una cita completada o previamente cancelada.

### Opción 18. Completar cita / guardar en historial

Introduce 18 y el ID de una cita pendiente. El sistema la marca como completada y registra la cita en el historial del paciente. No libera automáticamente al médico asignado mediante la fila.

### Opción 19. Mostrar todas las citas

Introduce 19 para visualizar las citas registradas, incluyendo su estado de cancelación o finalización.

### Opción 20. Mostrar historial de paciente

Introduce 20 y el ID del paciente. Se muestran las citas almacenadas en su historial en orden de registro.

### Opción 21. Mostrar citas de hoy

Introduce 21 para consultar las citas no canceladas cuya fecha coincide con la fecha local del sistema. Incluye citas completadas de ese día.

### Opción 0. Salir

Introduce 0 para finalizar el programa. Los datos de la sesión se pierden al cerrar la aplicación.

### Ejemplo de una consulta completa

Carga la demostración con 1. Selecciona 11 e introduce 101; utiliza 13 para asignarlo a D001. Consulta la asignación con 14 y el ID 101. Crea una cita con 16, ID 1, paciente 101, médico D001, fecha 2026-11-30, hora 10:00 y motivo Consulta general. Completa la cita con 18 e ID 1, libera al médico con 15 e ID 101 y consulta el historial con 20 e ID 101.

### Remover médicos y alcance del menú

La clase EmployeeManager ofrece removeDoctor(String id), que elimina al médico del registro y de las colecciones auxiliares. Por ejemplo, manager.removeDoctor("D003"). Esta operación se demuestra en TestEmployees, pero no tiene una opción en Main. No existe una operación de eliminación de pacientes ni una fila de urgencias en esta versión. La clase Visita es auxiliar; el historial utiliza objetos Cita.

### Manejo de errores y pruebas

El sistema utiliza excepciones personalizadas: FilaVaciaException, PacienteNoEncontradoException, MedicoNoDisponibleException, AsignacionInvalidaException y CitaConflictivaException. Main captura errores de ejecución y muestra mensajes; los métodos booleanos originales mantienen compatibilidad con otras clases.

Después de compilar, ejecute las pruebas desde la carpeta principal:

```bash
java -cp src TestEmployees
java -cp src TestIntegracionPacientesEmpleados
java -cp src TestExcepciones
```

Resultados verificados con OpenJDK 21 sobre la versión distribuida del repositorio: compilación exitosa, ejecución de TestEmployees, 16 verificaciones de integración y 33 pruebas de excepciones superadas. Estas pruebas no constituyen una validación exhaustiva de todas las entradas del menú.

## 5. Configuración

El programa utiliza las bibliotecas estándar java.util y java.time. No requiere dependencias externas, base de datos, variables de ambiente específicas del proyecto ni archivos adicionales de configuración. Java debe estar accesible desde la terminal. Los registros se almacenan en memoria y no persisten entre sesiones. La fecha de las citas de hoy depende del reloj local del equipo.

## 6. Autores y agradecimientos

Raquel Rivera Dompenciel  
Sergio Olivera Román  
Adrián R Sánchez González

Proyecto desarrollado en equipo para el curso de Estructuras de Datos. Se utilizan las bibliotecas estándar del lenguaje Java.

