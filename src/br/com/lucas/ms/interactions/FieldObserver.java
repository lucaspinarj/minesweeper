package br.com.lucas.ms.interactions;

@FunctionalInterface
public interface FieldObserver {

	public void event(Field field, FieldEvent event);
}
