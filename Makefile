.PHONY: help regression report

help:
	@cat Makefile  | grep  "^[a-z-].*" | grep -v help

test:
	curl -s http://localhost:5000/api/health || (echo api not launched; exit 1)
	curl -s http://localhost:8080 || (echo ui not launched; exit 1)
	echo "Launching @regression tests excluding @ignore in headless mode ..."
	mvn clean
	GITLAB_ENVIRONMENT_URL=http://localhost:8080 \
	 mvn test -Dcucumber.filter.tags='not @ignore' -Dheadless=true
	echo "Generating allure report and opening with the default browser ..."
	mkdir -p target/allure-results
	mvn allure:report
	docker rm -f bolt_nginx_e2e_allure || echo allure nginx already removed
	docker run --name bolt_nginx_e2e_allure -d -p 8083:80 -v `pwd`/target/site/allure-maven-plugin:/usr/share/nginx/html nginx
	open http://localhost:8083/index.html


