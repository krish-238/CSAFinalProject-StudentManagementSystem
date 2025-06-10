/*
 * Krish Senthil
 *
 * Period 1
 * APCSA - Final Project - Student Management System - SystemEntity Abstract Class
 * 05/28/2025
 *
 * The SchoolEntity class is the abstract base class for entities in the student management system,
 * defining common attributes like ID and name. It provides common methods for all entities,
 * reducing code duplication across students and other entities.
 */

/**
 * Abstract base class for all entities in the system, providing common attributes and methods.
 */
public abstract class SchoolEntity {
    private String id;
    private String name;
    private boolean isActive;

    public SchoolEntity(String id, String name) {
        if (id == null || id.isEmpty() || name == null || name.isEmpty()) {
            throw new IllegalArgumentException("ID and name cannot be null or empty");
        }
        this.id = id;
        this.name = name;
        this.isActive = true;
    }

    /**
     * Returns a string with the entity's details.
     * @return String containing id and name
     */
    public String getDetails() {
        String details = "ID: " + this.id + ", Name: " + this.name;
        return details;
    }

    /**
     * Updates the active status of the entity.
     * @param status the new status
     */
    public void updateStatus(boolean status) {
        this.isActive = status;
    }

    /**
     * Checks if the entity is active.
     * @return true if active, false otherwise
     */
    public boolean isActive() {
        return this.isActive;
    }

    // Getters for subclasses
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}