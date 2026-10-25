# Pruebas manuales de la API

Cuerpos JSON para probar los endpoints con curl o Postman. Los `test-*-invalid.json`
deben responder 400 (validación).

El login devuelve el token en una cookie, así que con curl se guarda en un archivo y se reutiliza:

```bash
cd pruebas-api

# Iniciar sesión (guarda la cookie en sesion.txt)
curl -c sesion.txt -H "Content-Type: application/json" \
     -d @admin-login.json http://localhost:8080/api/auth/login

# Usar la sesión en las demás peticiones
curl -b sesion.txt -H "Content-Type: application/json" \
     -d @curso.json http://localhost:8080/api/cursos
```

`sesion.txt` está en el `.gitignore`.
