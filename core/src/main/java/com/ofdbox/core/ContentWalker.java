package com.ofdbox.core;

import com.ofdbox.core.utils.MatrixUtils;
import com.ofdbox.core.utils.Stack;
import com.ofdbox.core.xmlobj.base.page.CT_PageBlock;
import com.ofdbox.core.xmlobj.graphic.CT_Path;
import com.ofdbox.core.xmlobj.object.composite.CT_Composite;
import com.ofdbox.core.xmlobj.object.image.CT_Image;
import com.ofdbox.core.xmlobj.object.text.CT_Text;
import com.ofdbox.core.xmlobj.pagedesc.CT_GraphicUnit;
import com.ofdbox.core.xmlobj.st.ST_Box;
import org.ujmp.core.Matrix;

public class ContentWalker {
    private CT_PageBlock ct_pageBlock;

    public ContentWalker(CT_PageBlock ct_pageBlock) {
        this.ct_pageBlock = ct_pageBlock;
    }

    public void onImage(CT_Image ctImage, Stack<CT_PageBlock> stack) {
    }

    public void onText(CT_Text ctText, Stack<CT_PageBlock> stack) {
    }

    public void onPath(CT_Path ctPath, Stack<CT_PageBlock> stack) {
    }

    public void onComposite(CT_Composite ctComposite, Stack<CT_PageBlock> stack) {
    }

    public void onPageBlock(CT_PageBlock ctPageBlock, Stack<CT_PageBlock> stack) {
        stack.push(ctPageBlock);
        if (ctPageBlock.getContent() == null) return;
        for (CT_GraphicUnit nObject : ctPageBlock.getContent()) {
            if (nObject instanceof CT_Image) {
                onImage((CT_Image) nObject, stack);
            } else if (nObject instanceof CT_Path) {
                onPath((CT_Path) nObject, stack);
            } else if (nObject instanceof CT_Text) {
                onText((CT_Text) nObject, stack);
            } else if (nObject instanceof CT_Composite) {
                onComposite((CT_Composite) nObject, stack);
            } else if (nObject instanceof CT_PageBlock) {
                onPageBlock((CT_PageBlock) nObject, stack);
            }
        }
    }


    public void walk() {
        onPageBlock(this.ct_pageBlock, new Stack<>());
    }
}
