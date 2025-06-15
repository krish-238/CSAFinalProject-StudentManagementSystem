/*
 * Krish Senthil
 *
 * Period 1
 * APCSA - Final Project - Student Management System - SystemEntity Abstract Class
 * 06/11/2025
 *
 * The SchoolEntity class is the abstract base class for entities in the student management system,
 * defining common attributes like ID and name. It provides common methods for all entities,
 * reducing code duplication across students and other entities.
 */

// Abstract base class for all entities/types of students in system
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

    /**
     * Gets the entity's ID.
     * @return The ID of the entity.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Gets the entity's name.
     * @return The name of the entity.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the entity's name.
     * @param name The new name for the entity.
     */
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }
}
