package com.ofdbox.core;

import com.ofdbox.core.utils.MatrixUtils;
import com.ofdbox.core.xmlobj.base.page.CT_PageBlock;
import com.ofdbox.core.xmlobj.base.page.object.*;
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

    public void onImage(CT_Image nImageObject, ST_Box boundary, Matrix matrix) {
    }

    public void onText(CT_Text nTextObject, ST_Box boundary, Matrix matrix) {
    }

    public void onPath(CT_Path nPathObject, ST_Box boundary, Matrix matrix) {
    }

    public void onComposite(CT_Composite nCompositeObject, ST_Box boundary, Matrix matrix) {
    }

    public void onPageBlock(CT_PageBlock ct_pageBlock, ST_Box boundary, Matrix matrix) {
        if (ct_pageBlock.getContent() == null) return;

        Matrix m = MatrixUtils.base();
        if (ct_pageBlock.getCtm() != null) {
            m = MatrixUtils.ctm(ct_pageBlock.getCtm());
        }
        if (ct_pageBlock.getBoundary() != null) {
            m = MatrixUtils.move(m, ct_pageBlock.getBoundary().getX(), ct_pageBlock.getBoundary().getY());
            if (boundary == null) {
                boundary = ct_pageBlock.getBoundary();
            }
        }
        matrix = m.mtimes(matrix);

        for (CT_GraphicUnit nObject : ct_pageBlock.getContent()) {
            if (nObject instanceof CT_Image) {
                onImage((CT_Image) nObject, boundary, matrix);
            } else if (nObject instanceof CT_Path) {
                onPath((CT_Path) nObject, boundary, matrix);
            } else if (nObject instanceof CT_Text) {
                onText((CT_Text) nObject, boundary, matrix);
            } else if (nObject instanceof CT_Composite) {
                onComposite((CT_Composite) nObject, boundary, matrix);
            } else if (nObject instanceof CT_PageBlock) {
                onPageBlock((CT_PageBlock) nObject, boundary, matrix);
            }
        }
    }


    public void walk() {
        onPageBlock(this.ct_pageBlock, null, MatrixUtils.base());
    }
}
