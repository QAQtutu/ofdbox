package com.ofdbox.core;

import com.ofdbox.core.xmlobj.base.page.CT_PageBlock;
import com.ofdbox.core.xmlobj.base.page.object.*;

public class ContentWalker {
    private CT_PageBlock ct_pageBlock;

    public ContentWalker(CT_PageBlock ct_pageBlock) {
        this.ct_pageBlock = ct_pageBlock;
    }

    public void onImage(NImageObject nImageObject) {
    }

    public void onText(NTextObject nTextObject) {
    }

    public void onPath(NPathObject nPathObject) {
    }

    public void onComposite(NCompositeObject nCompositeObject) {
    }

    public void onPageBlock(CT_PageBlock ct_pageBlock) {
        if(ct_pageBlock.getContent()==null)return;
        for (NObject nObject : ct_pageBlock.getContent()) {
            if (nObject instanceof NImageObject) {
                onImage((NImageObject) nObject);
            } else if (nObject instanceof NPageBlock) {
                onPageBlock((NPageBlock) nObject);
            } else if (nObject instanceof NPathObject) {
                onPath((NPathObject) nObject);
            } else if (nObject instanceof NTextObject) {
                onText((NTextObject) nObject);
            } else if (nObject instanceof NCompositeObject) {
                onComposite((NCompositeObject) nObject);
            }
        }
    }


    public void walk() {
        onPageBlock(this.ct_pageBlock);
    }
}
