# 하루살이 (Harusal2) - 일일 예산 관리 앱

하루살이는 월별 예산을 일 단위로 자동 배분하여 매일의 소비를 추적하고 관리할 수 있는 개인 가계부 앱입니다. 소비 상태에 따라 감정 피드백(이모지)을 제공하여 직관적인 지출 관리를 돕습니다.

## 주요 기능

- **월별 예산 관리** - 월 예산을 설정하면 일 단위로 자동 배분
- **실시간 소비 추적** - 수입/지출 내역 등록 및 잔여 예산 확인
- **감정 피드백 시스템** - 소비 상태에 따른 6단계 이모지 반응 (FLEX, CLAP, DEFAULT, EMBARRASSED, CRY, VOLCANO)
- **소비 내역 조회** - 날짜/예산 기간별 거래 내역 필터링
- **앱 위젯** - 홈 화면에서 예산 현황을 빠르게 확인 (5종 위젯)
- **알림 설정** - 소비 알림 푸시 기능
- **온보딩 튜토리얼** - 첫 실행 시 앱 사용법 안내

## 기술 스택

| 카테고리 | 기술 |
|---------|------|
| **Language** | Kotlin 1.8.0 |
| **Min SDK** | 26 (Android 8.0) |
| **Target SDK** | 34 (Android 14) |
| **Architecture** | MVVM |
| **DI** | Koin 2.2.1 |
| **Database** | Room 2.6.1 |
| **Async** | Coroutines 1.7.1 |
| **UI** | ViewBinding + DataBinding, XML Layouts |
| **Animation** | Lottie 3.4.1 |
| **UI Components** | Material Design 1.2.0, ConstraintLayout 2.1.4 |

## 프로젝트 구조

```
app/src/main/java/com/nexters/zzallang/harusal2/
├── application/          # App 초기화, DI 모듈, Database 설정
├── ui/                   # UI 레이어 (Activity, ViewModel, Adapter)
│   ├── main/             #   메인 화면
│   ├── history/          #   소비 내역
│   ├── setting/          #   설정
│   ├── statement/        #   거래 내역 등록
│   ├── budget/           #   예산 등록
│   ├── onboarding/       #   온보딩
│   ├── splash/           #   스플래시
│   └── widget/           #   앱 위젯 (5종)
├── data/                 # 데이터 레이어 (Repository, DAO, Entity)
├── usecase/              # 비즈니스 로직 레이어
├── keyboard/             # 커스텀 숫자 키보드 컴포넌트
└── constant/             # 상수 및 Enum
```

## 아키텍처

```
UI Layer (Activity / Fragment)
    ↓
ViewModel (LiveData 기반 상태 관리)
    ↓
UseCase (비즈니스 로직)
    ↓
Repository (데이터 접근 추상화)
    ↓
Room DAO & Entity (로컬 DB)
```

## 데이터베이스

| 테이블 | 설명 |
|--------|------|
| **budget** | 예산 기간 (금액, 시작일, 종료일) |
| **statement** | 거래 내역 (날짜, 내용, 금액, 예산 ID) |

## 빌드 및 실행

### 요구사항

- Android Studio Hedgehog 이상
- JDK 17
- Gradle 8.2.2

### 빌드

```bash
./gradlew assembleDebug
```

### 실행

Android Studio에서 프로젝트를 열고 Run 버튼을 클릭하거나:

```bash
./gradlew installDebug
```

## 앱 화면 흐름

```
SplashActivity
    ├── (첫 실행) → OnBoardingActivity → BudgetRegisterActivity → MainActivity
    └── (재실행) → MainActivity
```

## 소비 상태 시스템

| 상태 | 조건 | 설명 |
|------|------|------|
| FLEX | 일일 예산 70% 이상 잔여 | 훌륭한 소비 |
| CLAP | 일일 예산 여유 있음 | 좋은 소비 |
| DEFAULT | 오늘 거래 없음 | 기본 상태 |
| EMBARRASSED | 예산 초과 ~40% | 주의 필요 |
| CRY | 예산 초과 ~80% | 위험 |
| VOLCANO | 예산 극심한 초과 | 파산 경고 |

## 라이선스

이 프로젝트는 [Nexters](https://teamnexters.com) 팀에서 개발되었습니다.
