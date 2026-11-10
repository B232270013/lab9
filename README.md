# Лаборатори №9 — Код хамралт: JaCoCo-гоор тестээ хөтлөх

**Хичээл:** F.CSA313 — Программ хангамжийн чанарын баталгаа ба тест (2026)  
**Оюутан:** Б.Мөнгөншагай  
**Оюутны код:** B232270013  
**Репозиторий:** https://github.com/B232270013/lab9  
**Орчин:** Arch Linux, OpenJDK 17, Maven 3.10.0, JUnit 5, JaCoCo 0.8.12

## 1. Ажлын орчин

**`java --version`** 

```text
openjdk 17.0.21.0.3 2026-10-24
OpenJDK Runtime Environment (build 17.0.21.0.3+1)
OpenJDK 64-Bit Server VM (build 17.0.21.0.3+1, mixed mode, sharing)
```

`mvn --version` 

```text
Apache Maven 3.10.0 (c43a36b8d67be7e0805a411bc0898af1a51f5472)
Maven home: /usr/share/java/maven
Java version: 17.0.21.0.3, vendor: Arch Linux, runtime: /usr/lib/jvm/java-17-openjdk
Default locale: en_US, platform encoding: UTF-8, time zone: Asia/Ulaanbaatar
OS name: "linux", version: "6.18.60-1-lts", arch: "amd64", family: "unix"
```


## 2. Тестлэгдэж буй систем

`mn.edu.must.sqat.ScholarshipCalculator` класс нь дараах функцүүдтэй.

| Функц | Шаардлага |
|---|---|
| `letterGrade(double score)` | 90–100 → A, 80–89.99 → B, 70–79.99 → C, 60–69.99 → D, 0–59.99 → F; оноо 0–100-аас гадуур бол `IllegalArgumentException` |
| `gpa(double[] scores)` | Дүн бүрийг A=4, B=3, C=2, D=1, F=0 болгож дундажлах; `null` ба хоосон массивт exception |
| `scholarship(double gpa, int credits, boolean hasViolation)` | Сахилгын зөрчилтэй бол 0; голч ≥3.8 ба кредит ≥15 бол 100; голч ≥3.4 ба кредит ≥12 бол 70; голч ≥3.0 бол 50; бусад үед 0. Буруу голч эсвэл сөрөг кредитэд exception |

Шалгалтыг JUnit 5-аар хийж, JaCoCo Maven plugin-ээр instruction, branch, line хамралтыг хэмжсэн. Эх кодын зориудын зааг утгын алдааг D хэсэг хүртэл засварлалгүй хадгалсан.

## 3. Хэсэг A — Happy path ба эхний хэмжилт

Эхлээд зөвхөн хэвийн урсгалын дараах **5 тест** бичсэн.

| Тест | Оролт | Хүлээгдсэн үр дүн |
|---|---|---|
| `letterGradeA` | `letterGrade(95)` | `A` |
| `gpaTwoGrades` | `gpa([95,85])` | `3.5` |
| `scholarship100` | `scholarship(3.9,16,false)` | `100` |
| `scholarship70` | `scholarship(3.5,12,false)` | `70` |
| `scholarship50` | `scholarship(3.1,8,false)` | `50` |

Тестийг ажиллуулахад `Tests run: 5, Failures: 0, Errors: 0, Skipped: 0`, `BUILD SUCCESS` гарсан.

`results/jacoco-A.csv`-ийн бодит үзүүлэлтүүд:

| Хэмжүүр | Covered | Missed | Хамралт |
|---|---:|---:|---:|
| Instruction | 108 | 44 | 71.05% |
| Branch | 22 | 19 | 53.66% |
| Line | 16 | 9 | 64.00% |

Хэвийн урсгалын тестүүд `letterGrade()`-ийн бүх үсгэн дүн, оролтын хязгаарын exception-уудыг гүйцэд хамраагүй тул тайланд хүрээгүй (улаан) ба хэсэгчлэн хамрагдсан (шар) замууд үлдсэн. `gpa()`-ийн `null`, хоосон оролт, switch-ийн бусад салаа болон `scholarship()`-ийн сахилгын зөрчил, кредитийн хангалтгүй байдал, буруу оролтын замуудыг нэмэлт тестээр шалгах шаардлагатай болсон.

**Нотолгоо:** `results/mvn-verify-A.txt`, `results/jacoco-A.csv`, `evidence/first-total.png`, `evidence/first-se.png`, `evidence/first-test-ScholarshipCalculator.png`.

## 4. Хэсэг B — CFG болон 100% branch/line coverage

`scholarship()` функцийн CFG-г `cfg.png` файлд зурсан. Эхний шалгалтын `gpa < 0 || gpa > 4 || credits < 0` нөхцөл, сахилгын зөрчил, өндөр/дунд голч ба кредитийн `&&` нөхцөл, сүүлчийн `gpa >= 3.0` салааг ялган авч үзсэн. CFG-ийн True/False ирмэгүүдэд бодит JUnit тестүүдийн нэрийг тэмдэглэсэн.

Өмнөх 5 тест дээр **15 тест** нэмж, нийт **20 тест** ажиллуулсан. Нэмэлт тестүүдээр C/D/F дүнгүүд, онооны буруу оролт, `gpa()`-ийн `null`/хоосон массив болон бүх үсгэн дүн, `scholarship()`-ийн буруу голч/кредит, сахилгын зөрчил, кредит дутуу нөхцөлүүдийг шалгасан.

`results/mvn-verify-B.txt`-д `Tests run: 20, Failures: 0, Errors: 0, Skipped: 0` болон `BUILD SUCCESS` байна.

`results/jacoco-B.csv`-ийн бодит үзүүлэлтүүд:

| Хэмжүүр | Covered | Missed | Хамралт |
|---|---:|---:|---:|
| Instruction | 152 | 0 | 100% |
| Branch | 41 | **0** | **100%** |
| Line | 25 | **0** | **100%** |

**Нотолгоо:** `cfg.png`, `results/mvn-verify-B.txt`, `results/jacoco-B.csv`, `evidence/b-1.png`, `evidence/b-2.png`, `evidence/b-3.png`.

## 5. Хэсэг C — Давталтын заагийн 0/1/2+ тест

`gpa()` доторх давталтыг дараах **3 тестээр** шалгасан.

| Тест | Давталтын тохиолдол | Оролт | Хүлээгдсэн үр дүн |
|---|---|---|---|
| `loopZero` | 0 удаа | `[]` болон `null` | `IllegalArgumentException` (`assertThrows`) |
| `loopOne` | 1 удаа | `[95]` | `4.0` |
| `loopTwoOrMore` | 2+ удаа | `[95,85,75,65]` | `2.5` |

Нийт **23 тест** ажиллаж, `Failures: 0`, `Errors: 0`, `BUILD SUCCESS` гарсан (`results/mvn-verify-C.txt`).

Эдгээр тест нь B хэсэгт 100% болсон хамралтын хувийг өсгөхгүй байж болно, учир нь кодын бүх салаа өмнө нь аль хэдийн гүйцэтгэгдсэн. Гэхдээ хамралтын хувь нь массивын урт, давталтын тоо өөрчлөгдөх бүрд зөв хариу буцаж байгааг дангаар батлахгүй учраас 0, 1, 2+ тохиолдлын тест шаардлагатай.

## 6. Хэсэг D — 100% хамралттай ч зааг утгын алдаа байж болох нь

Спецификацид 60 оноо **D** байх ёстой. Дараах 7 зааг утгын тест нэмсэн:

`boundary60`, `boundary59_99`, `boundary69_99`, `boundary0`, `boundary100`, `boundary89_99`, `boundary90`.

Эх кодыг засахаас **өмнө** `mvn test` ажиллуулахад:

```text
Tests run: 30, Failures: 1, Errors: 0, Skipped: 0
ScholarshipCalculatorTest.boundary60:170 expected: <D> but was: <F>
BUILD FAILURE
```

гэсэн бодит алдаа гарсан. Алдаа нь `letterGrade()`-ийн `if (score > 60) return "D";` нөхцөлд байсан бөгөөд 60 оноог F гэж буруу ангилж байв.

**Алдаа илрүүлсэн commit:** `1966fc83` — `D failing test`.  
**Засварласан commit:** `d74726c2` — `Fixed d`.

Засвар:

```java
// Өмнө нь:
if (score > 60) return "D";
// Дараа нь:
if (score >= 60) return "D";
```

Засварын дараах `results/mvn-verify-final.txt`-д `Tests run: 30, Failures: 0, Errors: 0, Skipped: 0`, `BUILD SUCCESS` байна. `results/jacoco-final.csv`-д `BRANCH_MISSED=0`, `LINE_MISSED=0` буюу Branch **100% (41/41)**, Line **100% (25/25)** хэвээр гарсан.

### Гол дүгнэлт

Branch coverage 100% болсон байсан ч 60 онооны зааг утгын алдаа эхний тестүүдэд илрээгүй. `score > 60` ба `score >= 60` илэрхийлэл хоёулаа True болон False салаатай тул хоёр салааг гүйцэтгэснээр хамралт 100% болж чадна. Харин яг `60` утгыг оруулаагүй байхад нөхцөлийн заагт гарч буй буруу үр дүн мэдэгдэхгүй үлдсэн. `boundary60` тест D үр дүн хүлээсэн боловч F буцсан учраас алдааг илрүүлсэн. Код хамралт нь аль мөр, салаа ажилласныг хэмждэг боловч программын гаргасан үр дүн шаардлагатай тохирч байгааг дангаараа баталдаггүй. Тиймээс бүтцийн тестийг зааг утга болон спецификацид суурилсан тесттэй хослуулж хэрэглэх шаардлагатай.

**Нотолгоо:** `results/mvn-test-D-fail.txt`, `results/mvn-verify-final.txt`, `results/jacoco-final.csv`.

## 7. GitHub болон файлын бүтэц

```text
lab9/
├── .gitignore
├── README.md
├── pom.xml
├── cfg.png
├── src/
│   ├── main/java/mn/edu/must/sqat/ScholarshipCalculator.java
│   └── test/java/mn/edu/must/sqat/ScholarshipCalculatorTest.java
├── results/
│   ├── mvn-verify-A.txt
│   ├── jacoco-A.csv
│   ├── mvn-verify-B.txt
│   ├── jacoco-B.csv
│   ├── mvn-verify-C.txt
│   ├── mvn-test-D-fail.txt
│   ├── mvn-verify-final.txt
│   └── jacoco-final.csv
└── evidence/
    └── ... (screenshot)
```
## 9. Дүгнэлт

Энэ лабораторид JaCoCo ашиглан эхний 5 happy-path тестийн хамралтыг хэмжиж, дараа нь нөхцөл, алдаатай оролтын замуудыг шалгах 15 тест нэмснээр Branch болон Line хамралтыг 100%-д хүргэсэн. Давталтын 0/1/2+ тохиолдлыг тусгайлан шалгаж, тестийн чанар нь зөвхөн хамралтын хувиар хэмжигдэхгүй болохыг харуулсан. Дараа нь яг 60 онооны зааг утгын тест нэмэхэд өмнө нь 100% coverage-тэй байсан кодод алдаа байсныг илрүүлсэн. Алдааг зассаны дараа нийт 30 тест бүгд PASS болж, хамралт 100% хэвээр үлдсэн. Иймээс coverage нь тестийн хүрсэн бүтцийг хэмжих ч зөв үр дүнгийн баталгаа биш гэдэг дүгнэлтэд хүрсэн.
