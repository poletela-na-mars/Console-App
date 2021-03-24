# Console-App (ls)
Вывод содержимого указанной в качестве аргумента директории в виде отсортированного списка имен файлов.
____
## Использование


***Command Line: ls [-l] [-h] [-r] [-o output.file] directory_or_file***

***Actually will be: java -jar ls.jar [-l] [-h] [-r] [-o output.file] directory_or_file***

| Флаг | Описание |
|----:|:----:|
| -l (long) | переключает вывод в длинный формат, в котором, кроме имени файла, указываются права на выполнение/чтение/запись в виде битовой маски XXX, время последней модификации и размер в байтах | 
| -h (human-readable) | переключает вывод в человеко-читаемый формат (размер в кило-, мега- или гигабайтах, права на выполнение в виде rwx) |
| -r (reverse) | меняет порядок вывода на противоположный |
| -o (output) | указывает имя файла, в который следует вывести результат; если этот флаг отсутствует, результат выводится в консоль |

В случае, если в качестве аргумента указан файл, а не директория, следует вывести информацию об этом файле.