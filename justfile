## See https://github.com/casey/just
## Install with $ brew install just
test:
    ./gradlew :kotlin-testing:test
run:
    ./gradlew :kotlin-jvm:run
github:
    open https://github.com/LouisCAD/kotlin-libraries-playground/
issues:
    open https://github.com/LouisCAD/kotlin-libraries-playground/issues
prs:
    open https://github.com/LouisCAD/kotlin-libraries-playground/pulls
urls: github issues prs
    echo "URLs opened"
