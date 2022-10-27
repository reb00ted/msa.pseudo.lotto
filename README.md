# 프로젝트 개요(미완성)
마이크로 서비스 아키텍처를 공부하며 제작중인 토이프로젝트 ([pseudo.lotto](https://github.com/reb00ted/pseudo.lotto)의 MSA 버전)

# 프로젝트 설계
![architecture](https://user-images.githubusercontent.com/96247255/198269254-306bc59d-6629-44b2-92e8-2b99b8dc994c.PNG)


## 공개 API
### HTTP 응답 상태 코드
| 상태 코드 | 설명 |
|---|---|
| 200   | 성공 |
| 400   | 잘못된 요청 |
| 401   | 미인증 |
| 404   | 찾을 수 없음 |
| 500   | 내부 서버 오류 |
***

#### 회원 가입 `POST http://gateway/users/`
**Request Body:**
``` json
{
    "userId": "USER_ID", // 유저 아이디
    "password": "PASSWORD", // 패스워드
}
```

**Response Body:**
``` json
{
    "userId": "USER_ID",
    "createdAt": "2022-10-27T16:11:07.797025"
}
```
***

#### 로그인 `POST http://gateway/users/login`

**Request Body:**
``` json
{
    "userId": "USER_ID", // 유저 아이디
    "password": "PASSWORD", // 패스워드
}
```

**Response Cookie:** sessionId

**Response Body:**
``` json
{
    "userId": "USER_ID",
    "lastLoginAt": "2022-10-27T16:11:07.797025"
}
```
***

#### 포인트 충전 `POST http://gateway/point/charge`
**Cookie:**
sessionId  
**Request Body:**
``` json
{
    "amount": 1000 // 충전 금액
}
```

**Response Body:**
``` json
{
    "userId": "USER_ID",
    "amount": 충전 금액,
    "prevPoint": 충전 전 포인트,
    "currPoint": 현재 포인트,
    "createdAt": 처리 시각
}
```
***

#### 포인트 출금 `POST http://gateway/point/withdraw`
**Cookie:**
sessionId  
**Request Body:**
``` json
{
    "amount": 출금 금액
}
```

**Response Body:**
``` json
{
    "userId": "USER_ID",
    "amount": 출금 금액,
    "prevPoint": 출금 전 포인트,
    "currPoint": 현재 포인트,
    "createdAt": 처리 시각
}
```
***

#### 로또 구입 `POST http://gateway/lotto`
**Cookie:**
sessionId  
**Request Body:**
``` json
{
    "lottoRound": 로또 회차,
    "lottoNumbers": [1, 2, 3, 4, 5, 6]
}
```

**Response Body:**
``` json
{
    "txId": 트랜잭션 아이디,
    "userId": "USER_ID",
    "price": 가격,
    "lottoRound": 로또 회차,
    "lottoNumbers": [1, 2, 3, 4, 5, 6],
    "createdAt": 처리 시각
}
```
***

#### 현재 회차 정보 조회 `GET http://gateway/lotto/round/current`
**Response Body:**
``` json
{
    "round": 회차,
    "startDate": 회차 시작 시각,
    "endDate": 회차 종료 시각,
    "price": 복권 가격,
    "bonusNumber": null,
    "winningNumbers": null,
    "winnings": null
}
```
***

#### 지난 회차 정보 조회 `GET http://gateway/lotto/round/{round}`
**Response Body:**
``` json
{
    "round": 회차,
    "startDate": 회차 시작 시각,
    "endDate": 회차 종료 시각,
    "price": 복권 가격,
    "bonusNumber": 2등 보너스 번호
    "winningNumbers": [1, 2, 3, 4, 5, 6], // 당첨 번호
    "winnings": [1등, 2등, 3등, 4등, 5등, 0], // 당첨금
}
```
***

#### 마지막 종료 회차 조회 `GET http://gateway/lotto/round/last`
**Response Body:** round
***

#### 복권 구매 내역 조회 `GET http://gateway/lotto/list/{page}`
**Cookie:**
sessionId  
**Response Body:**
``` json
{
    "isFirst": boolean,
    "isLast": boolean,
    "prevPage": 이전 페이지 번호,
    "currentPage": 현재 페이지 번호,
    "nextPage": 다음 페이지 번호,
    "totalPage": 총 페이지 갯수,
    "pageList": [x1 ~ (x+1)0], // 현재 페이지 번호에 맞는 하단 페이지 번호 목록
    "lottoList": [
        {
            "txId": 트랜잭션 ID,
            "userId": "USER_ID",
            "round": 회차,
            "numbers": [{"number": 1, "matched": true/false}, ... ,{"number": 6, "matched": true/false}],
            "ranking": 당첨 순위,
            "winnings": 당첨금,
            "createdAt": 구입 시각
        }, ...
    ]
}
```
***

#### 포인트 변동 내역 조회 `GET http://gateway/point/history/{page}`
**Cookie:**
sessionId  
**Response Body:**
``` json
{
    "isFirst": boolean,
    "isLast": boolean,
    "prevPage": 이전 페이지 번호,
    "currentPage": 현재 페이지 번호,
    "nextPage": 다음 페이지 번호,
    "totalPage": 총 페이지 갯수,
    "pageList": [x1 ~ (x+1)0], // 현재 페이지 번호에 맞는 하단 페이지 번호 목록
    "pointTransactionList": [
        {
            "id": 고유 ID,
            "userId": "USER_ID",
            "txId": 트랜잭션 ID,
            "transactionType": 거래 타입,
            "amount": 금액,
            "createdAt": 거래 시각
        }, ...
    ]
}
```
***
