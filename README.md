# MiFinanzas

## Título del Proyecto  
**MiFinanzas**



##  Objetivos Iniciales

- Registrar y autenticar usuarios (inicio de sesión, cierre de sesión).
- Permitir registrar ingresos y gastos con fecha, cantidad y categoría.
- Categorizar los movimientos (alimentación, ocio, transporte, etc.).
- Definir presupuestos mensuales por categoría y generar alertas si se alcanza el límite.
- Establecer metas de ahorro con seguimiento del progreso.
- Mostrar resumen tipo dashboard con comparativa mensual e informes visuales.
- Filtros y buscador de movimientos por fecha, tipo y categoría.
- Exportación de datos a PDF o CSV.



## Resumen del Proyecto

*MiFinanzas* es una aplicación web local para la gestión de finanzas personales. Permitirá a los usuarios gestionar sus ingresos y gastos, organizarlos por categorías y visualizar sus finanzas a través de gráficos interactivos. Incluirá funcionalidades como metas de ahorro, presupuestos mensuales, alertas locales y exportación de datos. Está desarrollada con Java y Spring Boot, usando MongoDB como base de datos local, y una interfaz web sencilla hecha con HTML, CSS, JavaScript, Bootstrap y Thymeleaf.



##  Estado del Arte / Investigación

Aplicaciones como **Fintonic**, **Money Manager**, **YNAB** o **Wallet** ofrecen soluciones avanzadas para el control financiero. Muchas de ellas requieren conexión online, sincronización bancaria o suscripciones.  
*MiFinanzas* toma como referencia estas herramientas pero se centra en la simplicidad y el funcionamiento offline/local, integrando solo las funciones esenciales: control de ingresos/gastos, presupuestos, gráficos y exportación. Así, se adapta mejor al aprendizaje de desarrollo web y a usuarios que solo quieren llevar un control básico sin complicaciones.



##Introducción
El control de las finanzas personales es fundamental para mantener una buena salud económica. MiFinanzas nace como una solución sencilla y local para que cualquier usuario pueda gestionar sus ingresos, gastos, presupuestos y objetivos de ahorro sin depender de aplicaciones online o bancos externos. La aplicación busca ofrecer una experiencia intuitiva, enfocada en la organización y la visualización clara de la información financiera.


##Objetivos Definidos
Permitir a los usuarios registrar y autenticar su perfil (registro e inicio de sesión).

Registrar ingresos y gastos con datos como fecha, categoría y cantidad.

Crear categorías personalizadas para los movimientos financieros.

Establecer presupuestos mensuales por categoría y generar alertas locales si se supera.

Fijar metas de ahorro y mostrar el progreso hacia su cumplimiento.

Mostrar un resumen general (dashboard) con gráficos de barras y de pastel sobre ingresos, gastos, y saldo mensual.

Implementar filtros y un buscador de transacciones.

Permitir la exportación de los datos a formatos PDF o CSV.

Mantener todo el funcionamiento local, sin necesidad de servidores externos.



##Material y Recursos
Lenguaje Backend: Java 17

Framework Backend: Spring Boot

Base de Datos: MongoDB local (gestionada con MongoDB Compass)

Frontend: HTML5, CSS3, JavaScript, Bootstrap 5

Motor de plantillas: Thymeleaf

Entorno de desarrollo: IntelliJ IDEA

Herramientas de prueba: Postman para APIs REST

Control de versiones: Git y GitHub

Otros recursos: Diagrama de base de datos, esquemas de entidad, herramientas de diseño gráfico para mockups (opcional)



##Métodos Seguidos y Tecnologías
Para el desarrollo se aplicará una metodología ágil tipo kanban, planificando tareas semanales y avances parciales del proyecto.
Se utiliza Java con Spring Boot por su integración sencilla con MongoDB y su estructura modular, que facilita la organización del código.
MongoDB ha sido elegido por su flexibilidad con documentos JSON y facilidad de instalación local mediante MongoDB Compass.
Thymeleaf se integrará como motor de plantillas para renderizar dinámicamente las vistas HTML del proyecto.
Bootstrap se usará para garantizar una interfaz visual responsiva y moderna sin necesidad de diseñar CSS desde cero.
Todo el proceso se documentará y versionará en GitHub para tener trazabilidad y copias de seguridad del trabajo.


