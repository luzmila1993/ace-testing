FROM '#{aceServer}#'

ARG PROJECT_FOLDER
ARG PROJECT_TEST_FOLDER
ARG BAR_NAME
ARG TEST_BAR_NAME
ARG APP_REPO
ARG AGENT

USER root

ENV LICENSE=accept

RUN mkdir -p /home/aceuser/${PROJECT_TEST_FOLDER} && mkdir -p /home/aceuser/${PROJECT_FOLDER}

COPY ${PROJECT_TEST_FOLDER}/ /home/aceuser/${PROJECT_TEST_FOLDER}
COPY ${PROJECT_FOLDER}/ /home/aceuser/${PROJECT_FOLDER}
COPY shared-libraries/*/*/* /home/aceuser/shared-libraries/

RUN  chmod -R ugo+rwx /home/aceuser

USER 1000

RUN /bin/bash -c " \
    ibmint package --input-path /home/aceuser/${PROJECT_FOLDER} --output-bar-file ${BAR_NAME}.bar; \
    mqsicreateworkdir /tmp/UnitTestWorkDir; \
    ibmint deploy --input-path /home/aceuser --output-work-directory /tmp/UnitTestWorkDir"
    
RUN /bin/bash -c " \
    IntegrationServer --work-dir /tmp/UnitTestWorkDir --test-project $PROJECT_TEST_FOLDER --log-output-format ibmjson --start-msgflows false >> TEST-1.xml; \
    sed -i '/{.*}/d' TEST-1.xml"

USER root

RUN  chmod -R ugo+rwx /home/aceuser

ENV LICENSE accept
EXPOSE 7600
EXPOSE 7800

USER 1000