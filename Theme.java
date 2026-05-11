import java.awt.*;

// TODO: MAKE A FUNCTION TO CHANGE THE THEME COLORS, SO A NEW THEME OBJECT DOES NOT NEED TO BE CREATED

public class Theme {

    public String name = "";

    public Color unfocusedPanelBackground;
    public Color focusedPanelBackground;
    public Color unfocusedLabelForeground;
    public Color focusedLabelForeground;
    public Color frameBackground;

    public Color selectionColor;
    public Color spaceOnlyLineColor;
    public Color keywordColor;
    public Color operatorColor;
    public Color numberColor;
    public Color charColor;
    public Color stringColor;
    public Color commentColor;

    Theme(String themeName) {
        this.name = themeName;
        switch (themeName) {

            // ========== STANDARD THEMES ==========
            case "origin":
                // This theme was chosen while the syntax highlighting was being written and tested.
                // Never change it.
                this.unfocusedPanelBackground = new Color(31, 31, 31);
                this.focusedPanelBackground = new Color(230, 230, 230);
                this.unfocusedLabelForeground = new Color(230, 230, 230);
                this.focusedLabelForeground = new Color(31, 31, 31);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(80, 80, 80);

                this.keywordColor = new Color(255, 98, 98);
                this.operatorColor = new Color(230, 230, 230);
                this.charColor = new Color(255, 202, 86);
                this.stringColor = new Color(113, 209, 113);
                this.numberColor = new Color(94, 149, 255);
                this.commentColor = new Color(117, 147, 177);
                break;
            case "origin_light":
                this.unfocusedPanelBackground = new Color(255, 255, 255);
                this.focusedPanelBackground = new Color(30, 30, 30);
                this.unfocusedLabelForeground = new Color(30, 30, 30);
                this.focusedLabelForeground = new Color(255, 255, 255);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(149, 149, 149);

                this.keywordColor = new Color(158, 0, 0);
                this.operatorColor = new Color(30, 30, 30);
                this.charColor = new Color(138, 109, 46);
                this.stringColor = new Color(52, 119, 52);
                this.numberColor = new Color(61, 83, 172);
                this.commentColor = new Color(117, 147, 177);
                break;
            case "textora":
                this.unfocusedPanelBackground = new Color(31, 31, 31);
                this.focusedPanelBackground = new Color(210, 210, 210);
                this.unfocusedLabelForeground = new Color(210, 210, 210);
                this.focusedLabelForeground = new Color(31, 31, 31);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(90, 90, 90);

                this.keywordColor = new Color(0, 165, 40);
                this.operatorColor = new Color(210, 210, 210);
                this.charColor = new Color(237, 189, 86);
                this.stringColor = new Color(237, 189, 86);
                this.numberColor = new Color(211, 134, 155);
                this.commentColor = new Color(163, 114, 127);
                break;
            case "textora_light":
                this.unfocusedPanelBackground = new Color(255, 255, 255);
                this.focusedPanelBackground = new Color(31, 31, 31);
                this.unfocusedLabelForeground = new Color(31, 31, 31);
                this.focusedLabelForeground = new Color(255, 255, 255);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(149, 149, 149);

                this.keywordColor = new Color(10, 135, 40);
                this.operatorColor = new Color(31, 31, 31);
                this.charColor = new Color(165, 133, 56);
                this.stringColor = new Color(165, 133, 56);
                this.numberColor = new Color(211, 134, 155);
                this.commentColor = new Color(163, 114, 127);
                break;
            case "orange":
                this.unfocusedPanelBackground = new Color(20, 12, 10);
                this.focusedPanelBackground   = new Color(255, 120, 90);
                this.unfocusedLabelForeground = new Color(255, 120, 90);
                this.focusedLabelForeground   = new Color(20, 12, 10);
                this.frameBackground          = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(70, 40, 30);

                this.keywordColor  = new Color(255, 120, 90);
                this.operatorColor = new Color(255, 120, 90);
                this.charColor     = new Color(255, 120, 90);
                this.stringColor   = new Color(255, 120, 90);
                this.numberColor   = new Color(255, 120, 90);
                this.commentColor  = new Color(255, 120, 90);
                break;
            case "amber":
                this.unfocusedPanelBackground = new Color(0, 0, 0);
                this.focusedPanelBackground   = new Color(255, 171, 89);
                this.unfocusedLabelForeground = new Color(255, 171, 89);
                this.focusedLabelForeground   = new Color(0, 0, 0);
                this.frameBackground          = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(74, 50, 26);

                this.keywordColor  = new Color(255, 171, 89);
                this.operatorColor = new Color(255, 171, 89);
                this.charColor     = new Color(255, 171, 89);
                this.stringColor   = new Color(255, 171, 89);
                this.numberColor   = new Color(255, 171, 89);
                this.commentColor  = new Color(150, 110, 80);
                break;
            case "red":
                this.unfocusedPanelBackground = new Color(15, 10, 10);
                this.focusedPanelBackground = new Color(180, 70, 70);
                this.unfocusedLabelForeground = new Color(180, 70, 70);
                this.focusedLabelForeground = new Color(15, 10, 10);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(50, 30, 30);

                this.keywordColor = new Color(180, 70, 70);
                this.operatorColor = new Color(180, 70, 70);
                this.charColor = new Color(180, 70, 70);
                this.stringColor = new Color(180, 70, 70);
                this.numberColor = new Color(180, 70, 70);
                this.commentColor = new Color(120, 80, 80);
                break;
            case "midnight":
                this.unfocusedPanelBackground = new Color(29, 31, 33);
                this.focusedPanelBackground   = new Color(197, 200, 198);
                this.unfocusedLabelForeground = new Color(197, 200, 198);
                this.focusedLabelForeground   = new Color(29, 31, 33);
                this.frameBackground          = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(74, 74, 74);

                this.keywordColor  = new Color(178, 148, 187);
                this.operatorColor = new Color(197, 200, 198);
                this.charColor     = new Color(181, 189, 104);
                this.stringColor   = new Color(181, 189, 104);
                this.numberColor   = new Color(222, 147, 95);
                this.commentColor  = new Color(150, 152, 150);
                break;
            case "rust":
                this.unfocusedPanelBackground = new Color(28, 24, 20);
                this.focusedPanelBackground   = new Color(200, 185, 160);
                this.unfocusedLabelForeground = new Color(200, 185, 160);
                this.focusedLabelForeground   = new Color(28, 24, 20);
                this.frameBackground          = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(55, 50, 45);

                this.keywordColor  = new Color(197, 140, 82);
                this.operatorColor = new Color(200, 185, 160);
                this.charColor     = new Color(131, 159, 96);
                this.stringColor   = new Color(131, 159, 96);
                this.numberColor   = new Color(165, 135, 105);
                this.commentColor  = new Color(100, 95, 85);
                break;

            // ==============================
            //          MORE THEMES
            // ==============================

            case "origin_matte":
                this.unfocusedPanelBackground = new Color(22, 22, 20);
                this.focusedPanelBackground   = new Color(185, 180, 170);
                this.unfocusedLabelForeground = new Color(185, 180, 170);
                this.focusedLabelForeground   = new Color(22, 22, 20);
                this.frameBackground          = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(45, 45, 43);

                this.keywordColor  = new Color(160, 90, 90);
                this.operatorColor = new Color(185, 180, 170);
                this.charColor     = new Color(160, 159, 90);
                this.stringColor   = new Color(118, 160, 90);
                this.numberColor   = new Color(90, 133, 160);
                this.commentColor  = new Color(90, 96, 160);
                break;
            case "origin_matter":
                this.unfocusedPanelBackground = new Color(12, 12, 14);
                this.focusedPanelBackground   = new Color(160, 160, 160);
                this.unfocusedLabelForeground = new Color(160, 160, 160);
                this.focusedLabelForeground   = new Color(12, 12, 14);
                this.frameBackground          = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(94, 94, 94);

                this.keywordColor  = new Color(130, 110, 110);
                this.operatorColor = new Color(160, 160, 160);
                this.charColor     = new Color(130, 128, 110);
                this.stringColor   = new Color(110, 130, 111);
                this.numberColor   = new Color(110, 119, 130);
                this.commentColor  = new Color(100, 100, 140);
                break;
            case "velmora":
                this.unfocusedPanelBackground = new Color(41, 40, 40);
                this.focusedPanelBackground = new Color(212, 190, 152);
                this.unfocusedLabelForeground = new Color(212, 190, 152);
                this.focusedLabelForeground = new Color(41, 40, 40);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(149, 149, 149);

                this.keywordColor = new Color(234, 105, 98);
                this.operatorColor = new Color(212, 190, 152);
                this.charColor = new Color(216, 166, 87);
                this.stringColor = new Color(216, 166, 87);
                this.numberColor = new Color(211, 134, 155);
                this.commentColor = new Color(146, 131, 116);
                break;
            case "deepblue":
                this.unfocusedPanelBackground = new Color(50, 0, 154);
                this.focusedPanelBackground = new Color(192, 192, 192);
                this.unfocusedLabelForeground = new Color(192, 192, 192);
                this.focusedLabelForeground = new Color(50, 0, 154);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(120, 120, 120);

                this.keywordColor = new Color(192, 192, 192);
                this.operatorColor = new Color(192, 192, 192);
                this.charColor = new Color(192, 192, 192);
                this.stringColor = new Color(192, 192, 192);
                this.numberColor = new Color(192, 192, 192);
                this.commentColor = new Color(192, 192, 192);
                break;
            case "deepgray":
                this.unfocusedPanelBackground = new Color(192, 192, 192);
                this.focusedPanelBackground = new Color(50, 0, 154);
                this.unfocusedLabelForeground = new Color(50, 0, 154);
                this.focusedLabelForeground = new Color(192, 192, 192);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(133, 64, 255);

                this.keywordColor = new Color(50, 0, 154);
                this.operatorColor = new Color(50, 0, 154);
                this.charColor = new Color(50, 0, 154);
                this.stringColor = new Color(50, 0, 154);
                this.numberColor = new Color(50, 0, 154);
                this.commentColor = new Color(50, 0, 154);
                break;
            case "dark_matte":
                this.unfocusedPanelBackground = new Color(18, 20, 22);
                this.focusedPanelBackground   = new Color(180, 185, 190);
                this.unfocusedLabelForeground = new Color(180, 185, 190);
                this.focusedLabelForeground   = new Color(18, 20, 22);
                this.frameBackground          = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(69, 69, 69);

                this.keywordColor  = new Color(130, 160, 170);
                this.operatorColor = new Color(180, 185, 190);
                this.charColor     = new Color(170, 150, 130);
                this.stringColor   = new Color(140, 150, 135);
                this.numberColor   = new Color(150, 145, 120);
                this.commentColor  = new Color(90, 95, 100);
                break;
            case "darker_matte":
                this.unfocusedPanelBackground = new Color(12, 12, 14);
                this.focusedPanelBackground   = new Color(160, 160, 160);
                this.unfocusedLabelForeground = new Color(160, 160, 160);
                this.focusedLabelForeground   = new Color(12, 12, 14);
                this.frameBackground          = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(35, 35, 38);

                this.keywordColor  = new Color(110, 120, 130);
                this.operatorColor = new Color(160, 160, 160);
                this.charColor     = new Color(140, 130, 110);
                this.stringColor   = new Color(110, 120, 110);
                this.numberColor   = new Color(130, 115, 95);
                this.commentColor  = new Color(70, 70, 70);
                break;
            case "industrial":
                this.unfocusedPanelBackground = new Color(22, 22, 20);
                this.focusedPanelBackground   = new Color(185, 180, 170);
                this.unfocusedLabelForeground = new Color(185, 180, 170);
                this.focusedLabelForeground   = new Color(22, 22, 20);
                this.frameBackground          = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(45, 45, 43);

                this.keywordColor  = new Color(160, 130, 90);
                this.operatorColor = new Color(185, 180, 170);
                this.charColor     = new Color(120, 135, 115);
                this.stringColor   = new Color(120, 135, 115);
                this.numberColor   = new Color(140, 110, 110);
                this.commentColor  = new Color(100, 95, 85);
                break;
            case "industrial2":
                this.unfocusedPanelBackground = new Color(25, 28, 30);
                this.focusedPanelBackground   = new Color(150, 160, 165);
                this.unfocusedLabelForeground = new Color(150, 160, 165);
                this.focusedLabelForeground   = new Color(25, 28, 30);
                this.frameBackground          = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(91, 102, 113);

                this.keywordColor  = new Color(190, 130, 70);
                this.operatorColor = new Color(130, 160, 150);
                this.charColor     = new Color(180, 120, 140);
                this.stringColor   = new Color(140, 170, 200);
                this.numberColor   = new Color(210, 190, 120);
                this.commentColor  = new Color(85, 90, 95);
                break;
            case "industrial_light":
                this.unfocusedPanelBackground = new Color(255, 255, 255);
                this.focusedPanelBackground   = new Color(60, 60, 55);
                this.unfocusedLabelForeground = new Color(60, 60, 55);
                this.focusedLabelForeground   = new Color(255, 255, 255);
                this.frameBackground          = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(131, 131, 131);

                this.keywordColor  = new Color(180, 100, 70);
                this.operatorColor = new Color(60, 60, 55);
                this.charColor     = new Color(110, 150, 120);
                this.stringColor   = new Color(110, 150, 120);
                this.numberColor   = new Color(90, 120, 160);
                this.commentColor  = new Color(140, 140, 130);
                break;
            case "cobalt":
                this.unfocusedPanelBackground = new Color(8, 10, 15);
                this.focusedPanelBackground   = new Color(120, 180, 255);
                this.unfocusedLabelForeground = new Color(120, 180, 255);
                this.focusedLabelForeground   = new Color(8, 10, 15);
                this.frameBackground          = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(35, 45, 60);

                this.keywordColor  = new Color(120, 180, 255);
                this.operatorColor = new Color(120, 180, 255);
                this.charColor     = new Color(120, 180, 255);
                this.stringColor   = new Color(120, 180, 255);
                this.numberColor   = new Color(120, 180, 255);
                this.commentColor  = new Color(90, 120, 160);
                break;
            case "neon":
                this.unfocusedPanelBackground = new Color(20, 20, 25);
                this.focusedPanelBackground   = new Color(200, 255, 255);
                this.unfocusedLabelForeground = new Color(200, 255, 255);
                this.focusedLabelForeground   = new Color(20, 20, 25);
                this.frameBackground          = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(50, 105, 105);

                this.keywordColor  = new Color(200, 255, 255);
                this.operatorColor = new Color(200, 255, 255);
                this.charColor     = new Color(200, 255, 255);
                this.stringColor   = new Color(200, 255, 255);
                this.numberColor   = new Color(200, 255, 255);
                this.commentColor  = new Color(200, 255, 255);
                break;
            case "contrast":
                this.unfocusedPanelBackground = new Color(0, 0, 0); // ubuntu purple: 48, 10, 36
                this.focusedPanelBackground   = new Color(255, 255, 255);
                this.unfocusedLabelForeground = new Color(255, 255, 255);
                this.focusedLabelForeground   = new Color(0, 0, 0); // ubuntu purple: 48, 10, 36
                this.frameBackground          = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(0, 174, 50);

                this.keywordColor  = new Color(19, 184, 132);
                this.operatorColor = new Color(255, 255, 255);
                this.charColor     = new Color(255, 255, 255);
                this.stringColor   = new Color(200, 53, 53);
                this.numberColor   = new Color(135, 109, 199);
                this.commentColor  = new Color(72, 128, 184);
                break;
            case "contrast_light":
                this.unfocusedPanelBackground = new Color(255, 255, 255);
                this.focusedPanelBackground   = new Color(0, 0, 0);
                this.unfocusedLabelForeground = new Color(0, 0, 0);
                this.focusedLabelForeground   = new Color(255, 255, 255);
                this.frameBackground          = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(0, 255, 73);

                this.keywordColor  = new Color(19, 184, 132);
                this.operatorColor = new Color(0, 0, 0);
                this.charColor     = new Color(182, 179, 31);
                this.stringColor   = new Color(200, 53, 53);
                this.numberColor   = new Color(0, 0, 0);
                this.commentColor  = new Color(55, 122, 189);
                break;
            case "atorin":
                this.unfocusedPanelBackground = new Color(22, 22, 28);
                this.focusedPanelBackground   = new Color(150, 158, 162);
                this.unfocusedLabelForeground = new Color(150, 158, 162);
                this.focusedLabelForeground   = new Color(22, 22, 28);
                this.frameBackground          = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(70, 85, 74);

                this.keywordColor  = new Color(131, 159, 177);
                this.operatorColor = new Color(160, 64, 64);
                this.charColor     = new Color(145, 136, 96);
                this.stringColor   = new Color(107, 128, 108);
                this.numberColor   = new Color(139, 109, 149);
                this.commentColor  = new Color(90, 104, 112);
                break;
            case "covert":
                this.unfocusedPanelBackground = new Color(22, 22, 28);
                this.focusedPanelBackground   = new Color(150, 158, 162);
                this.unfocusedLabelForeground = new Color(150, 158, 162);
                this.focusedLabelForeground   = new Color(22, 22, 28);
                this.frameBackground          = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(70, 85, 74);

                this.keywordColor  = new Color(160, 64, 64);
                this.operatorColor = new Color(150, 158, 162);
                this.charColor     = new Color(150, 158, 162);
                this.stringColor   = new Color(150, 158, 162);
                this.numberColor   = new Color(150, 158, 162);
                this.commentColor  = new Color(150, 158, 162);
                break;
            case "red_light":
                this.unfocusedPanelBackground = new Color(255, 255, 255);
                this.focusedPanelBackground   = new Color(20, 20, 20);
                this.unfocusedLabelForeground = new Color(20, 20, 20);
                this.focusedLabelForeground   = new Color(255, 255, 255);
                this.frameBackground          = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(128, 128, 128);

                this.keywordColor  = new Color(195, 0, 0);
                this.operatorColor = new Color(20, 20, 20);
                this.charColor     = new Color(20, 20, 20);
                this.stringColor   = new Color(20, 20, 20);
                this.numberColor   = new Color(20, 20, 20);
                this.commentColor  = new Color(20, 20, 20);
                break;
            case "green_light":
                this.unfocusedPanelBackground = new Color(255, 255, 255);
                this.focusedPanelBackground   = new Color(20, 20, 20);
                this.unfocusedLabelForeground = new Color(20, 20, 20);
                this.focusedLabelForeground   = new Color(255, 255, 255);
                this.frameBackground          = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(128, 128, 128);

                this.keywordColor  = new Color(24, 129, 1);
                this.operatorColor = new Color(20, 20, 20);
                this.charColor     = new Color(20, 20, 20);
                this.stringColor   = new Color(20, 20, 20);
                this.numberColor   = new Color(20, 20, 20);
                this.commentColor  = new Color(20, 20, 20);
                break;
            case "blue_light":
                this.unfocusedPanelBackground = new Color(255, 255, 255);
                this.focusedPanelBackground   = new Color(20, 20, 20);
                this.unfocusedLabelForeground = new Color(20, 20, 20);
                this.focusedLabelForeground   = new Color(255, 255, 255);
                this.frameBackground          = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(128, 128, 128);

                this.keywordColor  = new Color(9, 31, 143);
                this.operatorColor = new Color(20, 20, 20);
                this.charColor     = new Color(20, 20, 20);
                this.stringColor   = new Color(20, 20, 20);
                this.numberColor   = new Color(20, 20, 20);
                this.commentColor  = new Color(20, 20, 20);
                break;
            case "red_dark":
                this.unfocusedPanelBackground = new Color(31, 31, 31);
                this.focusedPanelBackground = new Color(230, 230, 230);
                this.unfocusedLabelForeground = new Color(230, 230, 230);
                this.focusedLabelForeground = new Color(31, 31, 31);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(80, 80, 80);

                this.keywordColor = new Color(255, 98, 98);
                this.operatorColor = new Color(230, 230, 230);
                this.charColor = new Color(230, 230, 230);
                this.stringColor = new Color(230, 230, 230);
                this.numberColor = new Color(230, 230, 230);
                this.commentColor = new Color(230, 230, 230);
                break;
            case "green_dark":
                this.unfocusedPanelBackground = new Color(31, 31, 31);
                this.focusedPanelBackground = new Color(230, 230, 230);
                this.unfocusedLabelForeground = new Color(230, 230, 230);
                this.focusedLabelForeground = new Color(31, 31, 31);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(80, 80, 80);

                this.keywordColor = new Color(12, 193, 55);
                this.operatorColor = new Color(230, 230, 230);
                this.charColor = new Color(230, 230, 230);
                this.stringColor = new Color(230, 230, 230);
                this.numberColor = new Color(230, 230, 230);
                this.commentColor = new Color(230, 230, 230);
                break;
            case "blue_dark":
                this.unfocusedPanelBackground = new Color(31, 31, 31);
                this.focusedPanelBackground = new Color(230, 230, 230);
                this.unfocusedLabelForeground = new Color(230, 230, 230);
                this.focusedLabelForeground = new Color(31, 31, 31);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(80, 80, 80);

                this.keywordColor = new Color(77, 130, 205);
                this.operatorColor = new Color(230, 230, 230);
                this.charColor = new Color(230, 230, 230);
                this.stringColor = new Color(230, 230, 230);
                this.numberColor = new Color(230, 230, 230);
                this.commentColor = new Color(230, 230, 230);
                break;
            case "yellow_dark":
                this.unfocusedPanelBackground = new Color(31, 31, 31);
                this.focusedPanelBackground = new Color(230, 230, 230);
                this.unfocusedLabelForeground = new Color(230, 230, 230);
                this.focusedLabelForeground = new Color(31, 31, 31);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(80, 80, 80);

                this.keywordColor = new Color(255, 253, 151);
                this.operatorColor = new Color(230, 230, 230);
                this.charColor = new Color(230, 230, 230);
                this.stringColor = new Color(230, 230, 230);
                this.numberColor = new Color(230, 230, 230);
                this.commentColor = new Color(230, 230, 230);
                break;
            case "simple_light":
                this.unfocusedPanelBackground = new Color(255, 255, 255);
                this.focusedPanelBackground = new Color(30, 30, 30);
                this.unfocusedLabelForeground = new Color(30, 30, 30);
                this.focusedLabelForeground = new Color(255, 255, 255);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(124, 124, 124);

                this.keywordColor = new Color(67, 33, 161);
                this.operatorColor = new Color(30, 30, 30);
                this.charColor = new Color(30, 30, 30);
                this.stringColor = new Color(156, 26, 26);
                this.numberColor = new Color(30, 30, 30);
                this.commentColor = new Color(48, 149, 41);
                break;
            case "red_bloom":
                this.unfocusedPanelBackground = new Color(30, 16, 16);
                this.focusedPanelBackground = new Color(218, 101, 101);
                this.unfocusedLabelForeground = new Color(218, 101, 101);
                this.focusedLabelForeground = new Color(30, 16, 16);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(67, 36, 36);

                this.keywordColor = new Color(218, 101, 101);
                this.operatorColor = new Color(218, 101, 101);
                this.charColor = new Color(218, 101, 101);
                this.stringColor = new Color(218, 101, 101);
                this.numberColor = new Color(218, 101, 101);
                this.commentColor = new Color(218, 101, 101);
                break;
            case "green_bloom":
                this.unfocusedPanelBackground = new Color(16, 30, 19);
                this.focusedPanelBackground = new Color(115, 218, 140);
                this.unfocusedLabelForeground = new Color(115, 218, 140);
                this.focusedLabelForeground = new Color(16, 30, 19);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(36, 67, 42);

                this.keywordColor = new Color(115, 218, 140);
                this.operatorColor = new Color(115, 218, 140);
                this.charColor = new Color(115, 218, 140);
                this.stringColor = new Color(115, 218, 140);
                this.numberColor = new Color(115, 218, 140);
                this.commentColor = new Color(115, 218, 140);
                break;
            case "green_dim":
                this.unfocusedPanelBackground = new Color(0, 0, 0);
                this.focusedPanelBackground = new Color(0, 180, 0);
                this.unfocusedLabelForeground = new Color(0, 180, 0);
                this.focusedLabelForeground = new Color(0, 0, 0);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(0, 76, 0);

                this.keywordColor = new Color(0, 180, 0);
                this.operatorColor = new Color(0, 180, 0);
                this.numberColor = new Color(0, 180, 0);
                this.charColor = new Color(0, 180, 0);
                this.stringColor = new Color(0, 180, 0);
                this.commentColor = new Color(0, 180, 0);
                break;
            case "amber_dim":
                this.unfocusedPanelBackground = new Color(0, 0, 0);
                this.focusedPanelBackground = new Color(200, 120, 0);
                this.unfocusedLabelForeground = new Color(200, 120, 0);
                this.focusedLabelForeground = new Color(0, 0, 0);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(76, 48, 0);

                this.keywordColor = new Color(255, 172, 49);
                this.operatorColor = new Color(200, 120, 0);
                this.numberColor = new Color(200, 120, 0);
                this.charColor = new Color(200, 120, 0);
                this.stringColor = new Color(200, 120, 0);
                this.commentColor = new Color(200, 120, 0);
                break;
            case "white_dim":
                this.unfocusedPanelBackground = new Color(0, 0, 0);
                this.focusedPanelBackground = new Color(180, 180, 180);
                this.unfocusedLabelForeground = new Color(180, 180, 180);
                this.focusedLabelForeground = new Color(0, 0, 0);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(90, 90, 90);

                this.keywordColor = new Color(255, 255, 255);
                this.operatorColor = new Color(180, 180, 180);
                this.numberColor = new Color(180, 180, 180);
                this.charColor = new Color(180, 180, 180);
                this.stringColor = new Color(180, 180, 180);
                this.commentColor = new Color(180, 180, 180);
                break;
            case "dark_blue":
                this.unfocusedPanelBackground = new Color(30, 37, 67);
                this.focusedPanelBackground = new Color(160, 160, 193);
                this.unfocusedLabelForeground = new Color(160, 160, 193);
                this.focusedLabelForeground = new Color(30, 37, 67);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(80, 80, 113);

                this.keywordColor = new Color(160, 160, 193);
                this.operatorColor = new Color(160, 160, 193);
                this.charColor = new Color(160, 160, 193);
                this.stringColor = new Color(160, 160, 193);
                this.numberColor = new Color(160, 160, 193);
                this.commentColor = new Color(96, 96, 133);
                break;
            case "light_matte":
                this.unfocusedPanelBackground = new Color(255, 255, 255);
                this.focusedPanelBackground = new Color(0, 0, 0);
                this.unfocusedLabelForeground = new Color(0, 0, 0);
                this.focusedLabelForeground = new Color(255, 255, 255);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(101, 101, 101);

                this.keywordColor = new Color(0, 0, 128);
                this.operatorColor = new Color(0, 0, 0);
                this.charColor = new Color(128, 122, 0);
                this.stringColor = new Color(128, 0, 0);
                this.numberColor = new Color(13, 128, 0);
                this.commentColor = new Color(106, 137, 154);
                break;
            case "blocks":
                this.unfocusedPanelBackground = new Color(255, 255, 255);
                this.focusedPanelBackground = new Color(0, 0, 0);
                this.unfocusedLabelForeground = new Color(0, 0, 0);
                this.focusedLabelForeground = new Color(255, 255, 255);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(149, 149, 149);

                this.keywordColor = new Color(0, 0, 133);
                this.operatorColor = new Color(211, 16, 16);
                this.numberColor = new Color(234, 27, 151);
                this.charColor = new Color(53, 53, 158);
                this.stringColor = new Color(53, 53, 158);
                this.commentColor = new Color(142, 155, 211);
                break;
            default:
                this.name = "origin";
                this.unfocusedPanelBackground = new Color(31, 31, 31);
                this.focusedPanelBackground = new Color(230, 230, 230);
                this.unfocusedLabelForeground = new Color(230, 230, 230);
                this.focusedLabelForeground = new Color(31, 31, 31);
                this.frameBackground = this.unfocusedPanelBackground;

                this.spaceOnlyLineColor = new Color(80, 80, 80);

                this.keywordColor = new Color(255, 98, 98);
                this.operatorColor = new Color(230, 230, 230);
                this.charColor = new Color(255, 202, 86);
                this.stringColor = new Color(113, 209, 113);
                this.numberColor = new Color(94, 149, 255);
                this.commentColor = new Color(117, 147, 177);
                break;
        }
    }

    public final static String[] standardThemeNamesList = {
            "origin",
            "origin_light",
            "textora",
            "textora_light",
            "orange",
            "amber",
            "red",
            "midnight",
            "rust",
    };

    public final static String[] specialThemeNamesList = {
            "origin_matte",
            "origin_matter",
            "velmora",
            "deepblue",
            "deepgray",
            "dark_matte",
            "darker_matte",
            "industrial",
            "industrial2",
            "industrial_light",
            "cobalt",
            "neon",
            "contrast",
            "contrast_light",
            "atorin",
            "covert",
            "red_light",
            "green_light",
            "blue_light",
            "red_dark",
            "green_dark",
            "blue_dark",
            "yellow_dark",
            "simple_light",
            "red_bloom",
            "green_bloom",
            "green_dim",
            "amber_dim",
            "white_dim",
            "dark_blue",
            "light_matte",
            "blocks"
    };

    public static boolean themeExists(String themeName) {
        for (int i = 0, size = standardThemeNamesList.length; i < size; i++) {
            if (standardThemeNamesList[i].equals(themeName) == true) {
                return true;
            }
        }
        for (int i = 0, size = specialThemeNamesList.length; i < size; i++) {
            if (specialThemeNamesList[i].equals(themeName) == true) {
                return true;
            }
        }
        return false;
    }
}
