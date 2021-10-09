package com.muktadir.imagelibrary.views.image.photo.tools;

public class Tool {
    private String name;
    private int icon;
    private ToolType toolType;

   public Tool(String toolName, int toolIcon, ToolType toolType) {
        this.name = toolName;
        this.icon = toolIcon;
        this.toolType = toolType;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public ToolType getToolType() {
        return toolType;
    }
}
