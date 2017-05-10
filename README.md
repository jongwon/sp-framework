# sp-framework

## 프레임워크 소개 

sp framework 은 스프링을 기반으로 웹을 모듈 기반으로 만들기 위한 기본 프레임이 되는 모듈을 구상해서 만들고 있습니다.

이 프레임워크가 지향하는 바는 다음과 같습니다.

* 모듈은 그 자체로 어떠한 환경에서든 살아남을 수 있는 생명력을 가져야 한다.
* 모듈은 쪼개면 쪼갤수록 좋아진다.
* 모듈은 그 자체로 자신이 어떤 권한(auth)과 어떤 데이터(db)로 어떻게 표현되어야 하는지(ui)를 모두 설명할 수 있어야 한다.

## 주요 모듈 

기본 프레임에서 제공하는 모듈은 아래와 같다.
* sp-module-config
* sp-basic-spring-module
* sp-web-module
* sp-site-module
* sp-common-util

## 개발 환경

* sp framework v1 은 spring 4 를 기반으로 하고 있다.
* gradle 보다는 maven 기반으로 개발했다.
* xml 보다는 configuration 설정을 선호할 것이다. (어쩔 수 없는 경우에만 xml 설정을 사용하겠다.)
* web ui 는 thymeleaf 3 를 사용할 것을 권장한다. ( 이전에는 주로 freemarker 를 사용했었는데 스프링 설정이 갈수록 힘들어지고 있어서 thymeleaf 로 갈아타기로 결정했다. )
* 데이터베이스는 mongo db 를 사용할 것을 권장하지만 mysql 이나 hsql 같은 sql db 를 사용하는 것도 관계 없다.
* web 모듈과 domain 모듈을 분리해서 개발하는 것이 좋겠으나 많은 모듈에서 사용해야 하는 경우가 아니라면 같이 개발해서 사용하다 필요하면 모듈을 분리하는 전략을 사용하는 것도 괜찮다.



## 프레임워크 및 모듈 사용 

sp-framework 의 모듈을 사용하려면 pom.xml 파일에 아래와 같이 maven repository 를 추가해 주어야 한다.
```xml
	<repositories>
		<repository>
			<id>sp-framework</id>
			<name>sp-framework-repository</name>
			<url>http://maven.soreply.com/repository/maven-snapshots/</url>
		</repository>
	</repositories>
```


 