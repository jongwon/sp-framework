# sp-framework

sp framework 은 스프링을 기반으로 웹을 모듈 기반으로 만들기 위한 기본 프레임이 되는 모듈을 구상해서 만들고 있습니다.

이 프레임워크가 지향하는 바는 다음과 같습니다.

* 모듈은 그 자체로 어떠한 환경에서든 살아남을 수 있는 생명력을 가져야 한다.
* 모듈은 쪼개면 쪼갤수록 좋아진다.
* 모듈은 그 자체로 자신이 어떤 권한(auth)과 어떤 데이터(db)로 어떻게 표현되어야 하는지(ui)를 모두 설명할 수 있어야 한다.


sp-framework 의 모듈을 사용하려면 pom.xml 파일에 아래와 같이 maven repository 를 추가해 주어야 한다.

'''
	<repositories>
		<repository>
			<id>sp-framework</id>
			<name>sp-framework-repository</name>
			<url>http://maven.soreply.com/repository/maven-snapshots/</url>
		</repository>
	</repositories>
'''

---
 