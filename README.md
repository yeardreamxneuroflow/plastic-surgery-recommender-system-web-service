# 성형 추천을 위한 얼굴 랜드마크 추출 및 분류 모델 개발
> 이어드림스쿨X뉴로플로우
> 
> 모델 WEB 인터페이스를 제공함으로서, 서비스의 편의성과 유지보수성을 제공한다.
[성형수술 추천 시스템 Repo](https://github.com/yeardreamxneuroflow/plastic-surgery-recommender-system)

## 개발 기간
- 23.11.06 ~ 23.12.29


## 개발 환경
- `java 17`
- `JDK 1.8.0`
- *IDE* : IntelliJ
- *Framework* : Springboot(3.*)
- *Database* : MariaDB
- *Cloud* : AWS EC2, S3
- *Model* : Marqo, Mediapipe
  

![Neuroflow_Architecture drawio](https://github.com/yeardreamxneuroflow/plastic-surgery-recommender-system-web-service/assets/46197893/f3418184-cfce-4659-8f8e-2f6081c611d7)

## 프로젝트 설명

- index.html
> <img width="1443" alt="Screenshot 2024-01-03 at 3 38 21 PM" src="https://github.com/yeardreamxneuroflow/plastic-surgery-recommender-system-web-service/assets/46197893/a45d1ec5-11c7-4c9d-a368-d3ff904df1e1">
회원 가입 및 인증 시 서비스 시작 가능.

- result.html
<img width="1445" alt="KakaoTalk_Photo_2024-01-03-16-43-29" src="https://github.com/yeardreamxneuroflow/plastic-surgery-recommender-system-web-service/assets/46197893/383274d7-7bed-49f3-9ffc-596bb293794a">
인증 후 서비스를 위한 Client Image 를 전송 후 result 출력.

## 주요 기능
#### 인증
- Spring Security의 기본 Session Cookie 사용
- 인증 정보에 대한 데이터는 Maria DB로 관리(JDBC 사용)

#### Front
- 빠른 러닝커브를 위해 SSR인 Thymeleaf사용

#### Log
- 입력 이미지 S3 에 저장
- 서비스 이용시 로그를 저장
  - 회원 정보, 서비스 이용 일시, 입력 이미지 S3 filekey, URL, 출력 이미지의 S3 file key, URL

#### 

