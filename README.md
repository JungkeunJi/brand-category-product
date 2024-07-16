# 브랜드 카테고리 상품 [MUSINSA]

## 구현 범위
8개의 카테고리(상의~액세서리)와 9개의 브랜드(A~I)가 있고, 특정 브랜드의 특정 카테고리에는 가격만 존재하는 단 1개의 상품만이 존재한다고 가정합니다. 
이 데이터를 기반으로 다음 내용을 구현합니다.
1. 고객은 카테고리 별로 최저가격인 브랜드와 가격을 조회하고 총액이 얼마인지 확인할 수 있어야 합니다.
2. 고객은 단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액이 얼마인지 확인할 수 있어야 합니다.
3. 고객은 특정 카테고리에서 최저가격 브랜드와 최고가격 브랜드를 확인하고 각 브랜드 상품의 가격을 확인할 수 있어야 합니다.
4. 운영자는 새로운 브랜드를 등록하고, 모든 브랜드의 상품을 추가, 변경, 삭제할 수 있어야 합니다.

### 구현 1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
HTTP request
```
GET /api/categories/lowest-price
Host: http://localhost:8080
```
<details>
<summary> (클릭) HTTP response </summary>

``` json
{
  "data": {
    "categoryProducts": [
      {
        "categoryId": 1,
        "categoryName": "상의",
        "product": {
          "id": 17,
          "price": 10000,
          "brandId": 3,
          "brandName": "C"
        }
      },
      {
        "categoryId": 2,
        "categoryName": "아우터",
        "product": {
          "id": 34,
          "price": 5000,
          "brandId": 5,
          "brandName": "E"
        }
      },
      {
        "categoryId": 3,
        "categoryName": "바지",
        "product": {
          "id": 27,
          "price": 3000,
          "brandId": 4,
          "brandName": "D"
        }
      },
      {
        "categoryId": 4,
        "categoryName": "스니커즈",
        "product": {
          "id": 52,
          "price": 9000,
          "brandId": 7,
          "brandName": "G"
        }
      },
      {
        "categoryId": 5,
        "categoryName": "가방",
        "product": {
          "id": 5,
          "price": 2000,
          "brandId": 1,
          "brandName": "A"
        }
      },
      {
        "categoryId": 6,
        "categoryName": "모자",
        "product": {
          "id": 30,
          "price": 1500,
          "brandId": 4,
          "brandName": "D"
        }
      },
      {
        "categoryId": 7,
        "categoryName": "양말",
        "product": {
          "id": 71,
          "price": 1700,
          "brandId": 9,
          "brandName": "I"
        }
      },
      {
        "categoryId": 8,
        "categoryName": "액세서리",
        "product": {
          "id": 48,
          "price": 1900,
          "brandId": 6,
          "brandName": "F"
        }
      }
    ],
    "totalPrice": 34100
  }
}
```
</details>

### 구현 2. 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API
HTTP request
```
GET /api/brands/lowest-total-price
Host: http://localhost:8080
```
<details>
<summary> (클릭) HTTP response </summary>

``` json
{
  "data": {
    "id": 4,
    "name": "D",
    "categoryProducts": [
      {
        "id": 25,
        "price": 10100,
        "categoryId": 1,
        "categoryName": "상의"
      },
      {
        "id": 26,
        "price": 5100,
        "categoryId": 2,
        "categoryName": "아우터"
      },
      {
        "id": 27,
        "price": 3000,
        "categoryId": 3,
        "categoryName": "바지"
      },
      {
        "id": 28,
        "price": 9500,
        "categoryId": 4,
        "categoryName": "스니커즈"
      },
      {
        "id": 29,
        "price": 2500,
        "categoryId": 5,
        "categoryName": "가방"
      },
      {
        "id": 30,
        "price": 1500,
        "categoryId": 6,
        "categoryName": "모자"
      },
      {
        "id": 31,
        "price": 2400,
        "categoryId": 7,
        "categoryName": "양말"
      },
      {
        "id": 32,
        "price": 2000,
        "categoryId": 8,
        "categoryName": "액세서리"
      }
    ],
    "totalPrice": 36100
  }
}
```
</details>

### 구현 3. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
HTTP request
```
GET /api/categories/price-range?name=상의
Host: http://localhost:8080
```
<details>
<summary> (클릭) HTTP response </summary>

``` json
{
  "data": {
    "id": 1,
    "name": "상의",
    "lowestPriceProducts": [
      {
        "id": 17,
        "price": 10000,
        "brandId": 3,
        "brandName": "C"
      }
    ],
    "highestPriceProducts": [
      {
        "id": 65,
        "price": 11400,
        "brandId": 9,
        "brandName": "I"
      }
    ]
  }
}
```
</details>

### 구현 4. 브랜드 및 상품을 추가/업데이트/삭제하는 API
#### 브랜드 및 상품 추가
HTTP request
```
POST /api/brands
Content-Type: application/json
Host: http://localhost:8080

{
  "name": "Z",
  "categoryProducts": [
    {
      "categoryName": "상의",
      "productPrice": 1000
    },
    {
      "categoryName": "아우터",
      "productPrice": 2000
    },
    {
      "categoryName": "바지",
      "productPrice": 3000
    },
    {
      "categoryName": "스니커즈",
      "productPrice": 4000
    },
    {
      "categoryName": "가방",
      "productPrice": 5000
    },
    {
      "categoryName": "모자",
      "productPrice": 6000
    },
    {
      "categoryName": "양말",
      "productPrice": 7000
    },
    {
      "categoryName": "액세서리",
      "productPrice": 8000
    }
  ]
}
```
<details>
<summary> (클릭) HTTP response </summary>

``` json
{
  "data": {
    "id": 10,
    "name": "Z",
    "categoryProducts": [
      {
        "id": 73,
        "price": 5000,
        "categoryId": 5,
        "categoryName": "가방"
      },
      {
        "id": 74,
        "price": 6000,
        "categoryId": 6,
        "categoryName": "모자"
      },
      {
        "id": 75,
        "price": 3000,
        "categoryId": 3,
        "categoryName": "바지"
      },
      {
        "id": 76,
        "price": 1000,
        "categoryId": 1,
        "categoryName": "상의"
      },
      {
        "id": 77,
        "price": 4000,
        "categoryId": 4,
        "categoryName": "스니커즈"
      },
      {
        "id": 78,
        "price": 2000,
        "categoryId": 2,
        "categoryName": "아우터"
      },
      {
        "id": 79,
        "price": 8000,
        "categoryId": 8,
        "categoryName": "액세서리"
      },
      {
        "id": 80,
        "price": 7000,
        "categoryId": 7,
        "categoryName": "양말"
      }
    ]
  }
}
```
</details>

#### 브랜드의 상품 수정
HTTP request
```
PUT /api/brands/A
Content-Type: application/json
Host: http://localhost:8080

{
  "categoryProducts": [
    {
      "categoryName": "바지",
      "productPrice": 3000
    },
    {
      "categoryName": "스니커즈",
      "productPrice": 4000
    },
    {
      "categoryName": "가방",
      "productPrice": 5000
    },
    {
      "categoryName": "모자",
      "productPrice": 6000
    },
    {
      "categoryName": "양말",
      "productPrice": 7000
    },
    {
      "categoryName": "액세서리",
      "productPrice": 8000
    }
  ]
}
```
<details>
<summary> (클릭) HTTP response </summary>

``` json
{
  "data": {
    "id": 1,
    "name": "A",
    "categoryProducts": [
      {
        "id": 1,
        "price": 11200,
        "categoryId": 1,
        "categoryName": "상의"
      },
      {
        "id": 2,
        "price": 5500,
        "categoryId": 2,
        "categoryName": "아우터"
      },
      {
        "id": 3,
        "price": 3000,
        "categoryId": 3,
        "categoryName": "바지"
      },
      {
        "id": 4,
        "price": 4000,
        "categoryId": 4,
        "categoryName": "스니커즈"
      },
      {
        "id": 5,
        "price": 5000,
        "categoryId": 5,
        "categoryName": "가방"
      },
      {
        "id": 6,
        "price": 6000,
        "categoryId": 6,
        "categoryName": "모자"
      },
      {
        "id": 7,
        "price": 7000,
        "categoryId": 7,
        "categoryName": "양말"
      },
      {
        "id": 8,
        "price": 8000,
        "categoryId": 8,
        "categoryName": "액세서리"
      }
    ]
  }
}
```
</details>

#### 브랜드 삭제
HTTP request
```
DELETE /api/brands/A
Host: http://localhost:8080
```
<details>
<summary> (클릭) HTTP response </summary>

``` json
{
  "data": null
}
```
</details>

## 프로젝트 빌드
프로젝트는 다음 기술을 포함합니다.
- 스프링부트 3.3.1
- 코틀린
- Gradle
- JPA
- Embedded H2

다음 터미널 명령어를 통해 빌드할 수 있습니다.
```
./gradlew build
```

## 프로젝트 구성
프로젝트는 멀티 모듈로 구성되어 있습니다. 각 모듈은 Gradle 을 가지며, 자신의 Layer 에 해당하는 의존성을 가집니다.
### api-rest 모듈
- 실행 가능한 API 어플리케이션을 구성하는 모듈로 최상위 레이어
- 모든 하위 모듈을 의존성으로 가짐(application, domain)
- client 로부터의 요청 및 응답 처리 (api 정의)
- 웹 계층에서 요청에 대한 적절한 응답 또는 예외 형식 처리

### application 모듈
- api-rest 모듈에서 호출하여 트랜잭션 단위의 기능 수행
- 하위에 domain 모듈을 가지며, domain 모듈의 모델과 서비스 로직을 사용하여 기능을 수행하고 적절한 응답값으로 변환하여 전달

### domain 모듈
- 엔티티와 도메인 서비스, 레포지토리를 포함하는 모듈로 최하위 레이어
- 도메인 서비스를 통해 비즈니스 로직 처리

## 실행 및 테스트 방법
### Application 실행
프로젝트 터미널에서 다음 명령어를 통해 application 을 실행시킬 수 있습니다.
```
./gradlew -p api-rest bootRun
```
또는 프로젝트의 api-rest 모듈에서 BrandCategoryProductApplication 클래스의 main 함수를 통해 application 을 실행시킬 수 있습니다.
Application 실행 시 다음 내용이 자동 수행됩니다.
- Embedded H2 실행
- DB 스키마 자동 생성(JPA ddl-auto: create 사용)
- DB 테이블 데이터 초기 세팅(data.sql)
  - data.sql 파일은 api-rest 모듈의 resources 디렉토리에 있습니다

### Application 테스트
Application 실행 후 모든 구현에 대한 API 테스트는 apitest.http 파일로 수행할 수 있습니다. apitest.http 파일은 api-rest 모듈에 있습니다.

## DB 스키마