# Jenkins Practice Project — Java/Maven backend + npm frontend

A minimal full-stack app built specifically for practicing Jenkins pipelines:
a Spring Boot backend built with Maven (packaged as a WAR, deployable
straight into your Tomcat), and a React frontend built with npm.

## Structure

```
sample-cicd-project/
├── backend/          Spring Boot app (Maven, packaging=war)
├── frontend/          React app (npm, react-scripts)
├── Jenkinsfile        Pipeline: build both, archive artifacts, deploy WAR
└── README.md
```

## Fields meant to be edited before each deploy

This is the point of the project — change these, rebuild, redeploy, and
watch the change show up, to get a feel for a real edit → build → deploy loop.

| File | Field | Purpose |
|---|---|---|
| `backend/src/main/resources/application.properties` | `app.message`, `app.version` | Shown by the `/api/greeting` endpoint |
| `backend/pom.xml` | `<version>`, `<finalName>` | Artifact version / WAR filename |
| `frontend/src/config.js` | `API_BASE_URL`, `APP_NAME` | Where the frontend looks for the backend, and page title |

## Running locally without Jenkins (sanity check first)

**Backend:**
```
cd backend
mvn clean package
java -jar target/greeting-app.war
```
Visit `http://localhost:8080/api/greeting` (embedded server, default port —
change with `server.port` in application.properties if 8080 is taken, e.g.
by Jenkins itself).

**Frontend:**
```
cd frontend
npm install
npm start
```
Visit `http://localhost:3000`.

## Deploying to your Tomcat instead

```
cd backend
mvn clean package
sudo cp target/greeting-app.war /var/lib/tomcat10/webapps/
```
Tomcat auto-explodes it. Visit:
`http://localhost:8081/greeting-app/api/greeting`
(adjust the port to whatever you set in Tomcat's server.xml)

Then update `frontend/src/config.js` → `API_BASE_URL` to match, rebuild the
frontend, and open `frontend/build/index.html` or serve it via `npx serve -s build`.

## Setting this up in Jenkins

1. Push this folder to a Git repo (or SVN, once you're ready to switch).
2. In Jenkins: **Manage Jenkins > Tools** → add a NodeJS installation, name
   it `node20` (or update the `Jenkinsfile`'s `tools` block to match your name).
3. Confirm Maven is available: `Manage Jenkins > Tools` → add a Maven
   installation if `mvn` isn't already on the Jenkins user's PATH.
4. **New Item > Pipeline** → **Pipeline script from SCM** → point at your repo.
   The `Jenkinsfile` in this folder is picked up automatically.
5. Run the build. Check each stage: Maven build, npm build, archived
   artifacts (downloadable from the build page), and the Tomcat deploy step.

### Note on the deploy stage

The `Jenkinsfile`'s deploy stage does `cp ... /var/lib/tomcat10/webapps/`.
The Jenkins service runs as its own `jenkins` user, which won't have
permission to write there by default. For local practice, either:
- add the `jenkins` user to the `tomcat` group and adjust folder
  permissions, or
- skip the deploy stage for now and just confirm the archived WAR builds
  correctly — that's the core Jenkins skill being practiced anyway.
