package com.github.dkharrat.nexusdialog;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import com.github.dkharrat.nexusdialog.util.ObjectUtil;

/**
 * <code>FormModel</code> is an abstract class that represents the backing data for a form. It provides a mechanism
 * for form elements to retrieve their values to display to the user and persist changes to the model upon changes.
 */
public abstract class FormModel {

    private final PropertyChangeSupport m_propertyChangeSupport = new PropertyChangeSupport(this);

    /**
     * This method is called when a form element changes its value through user input or external changes. Subclasses
     * must implement this method to update the backing model.
     *
     * @param name      the field name to set the value for
     * @param newValue  the value to set
     */
    protected abstract void setBackingValue(String name, Object newValue);

    /**
     * This method is called whenever the form needs the current value for a specific field. Subclasses must implement
     * this method to provide the form access to the backing model.
     *
     * @param name      the field name to retrieve the value for
     * @return          the current value of the specified field
     */
    protected abstract Object getBackingValue(String name);

    /**
     * Returns the value for the specified field name.
     *
     * @param name  the field name
     * @return      the value currently set for the specified field name
     */
    public final Object getValue(String name) {
        return getBackingValue(name);
    }

    /**
     * Sets a value for the specified field name. A property change notification is fired to registered listeners if
     * the field's value changed.
     *
     * @param name      the field name to set the value for
     * @param newValue  the value to set
     */
    public final void setValue(String name, Object newValue) {
        Object curValue = getBackingValue(name);
        if (!ObjectUtil.objectsEqual(curValue, newValue)) {
            setBackingValue(name, newValue);
            m_propertyChangeSupport.firePropertyChange(name, curValue, newValue);
        }
    }

    /**
     * Subscribes {@code listener} to change notifications for all properties.
     *
     * @see {@link PropertyChangeSupport#addPropertyChangeListener}
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        m_propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Unsubscribes {@code listener} from change notifications for all properties.
     *
     * @see {@link PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)}
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        m_propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
