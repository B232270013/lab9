# Лаборатори №9: Код хамралт — JaCoCo-гоор тестээ хөтлөх

**Хичээл:** F.CSA313 — Программ хангамжийн чанарын баталгаа ба тест  
**Оюутан:** Б.Мөнгөншагай  
**Оюутны код:** B232270013  
**Гүйцэтгэсэн орчин:** Arch Linux, Java 17, Maven 3.10.0, JUnit 5, JaCoCo 0.8.12  


## 1. Шалгагдах систем

`mn.edu.must.sqat.ScholarshipCalculator` класс нь гурван үндсэн функцтэй:

| Функц | Тодорхойлолт |
|---|---|
| `letterGrade(double score)` | 0–100 оноог A/B/C/D/F үнэлгээнд хөрвүүлнэ; хязгаараас гадуур бол `IllegalArgumentException` шиднэ. |
| `gpa(double[] scores)` | A=4, B=3, C=2, D=1, F=0 зарчмаар голч бодно; `null` болон хоосон массивыг хүлээн авахгүй. |
| `scholarship(double gpa, int credits, boolean hasViolation)` | Голч, кредит, сахилгын зөрчлөөс хамаарч тэтгэлгийн 0/50/70/100 хувийг буцаана. |

Энэ лабораторид ашиглаж байгаа `pom.xml` нь JUnit 5, JaCoCo-г урьдчилан тохируулсан starter төслийнх юм.

## 2. Хэсэг A — Happy path тестүүд ба эхний хэмжилт

`ScholarshipCalculatorTest.java`-д дараах таван хэвийн урсгалын тест бичсэн:

| Тестийн нэр | Оролт | Хүлээгдсэн үр дүн |
|---|---|---|
| `letterGradeA` | `letterGrade(95)` | `"A"` |
| `gpaTwoGrades` | `gpa([95, 85])` | `3.5` |
| `scholarship100` | `scholarship(3.9, 16, false)` | `100` |
| `scholarship70` | `scholarship(3.5, 12, false)` | `70` |
| `scholarship50` | `scholarship(3.1, 8, false)` | `50` |

### 4.1. Туршилтын үр дүн

Дараах командыг ажиллуулж Maven-ийн гаралтыг хадгалсан:

```bash
mvn verify 2>&1 | tee results/mvn-verify-A.txt
```

Бодит гаралт:

```text
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

JaCoCo-ийн CSV тайланг дараах командаар хадгалах шаардлагатай:

```bash