package main.ru.geekbrains.clientside.converter;

public interface Converter<Source, Target>
{

    Target convert(Source source);
}
