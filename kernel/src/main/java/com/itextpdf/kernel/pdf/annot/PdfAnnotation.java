/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
    Authors: Bruno Lowagie, Paulo Soares, et al.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfIndirectReference;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.layer.IPdfOCG;

/**
 * This is a super class for the annotation dictionary wrappers. Derived classes represent
 * different standard types of annotations. See ISO-320001 12.5.6, “Annotation Types.”
 */
public abstract class PdfAnnotation extends PdfObjectWrapper<PdfDictionary> {

    private static final long serialVersionUID = -6555705164241587799L;

    /**
     * Annotation flag.
     * See also {@link PdfAnnotation#setFlag(int)} and ISO-320001, table 165.
     */
    public static final int INVISIBLE = 1;

    /**
     * Annotation flag.
     * See also {@link PdfAnnotation#setFlag(int)} and ISO-320001, table 165.
     */
    public static final int HIDDEN = 2;

    /**
     * Annotation flag.
     * See also {@link PdfAnnotation#setFlag(int)} and ISO-320001, table 165.
     */
    public static final int PRINT = 4;

    /**
     * Annotation flag.
     * See also {@link PdfAnnotation#setFlag(int)} and ISO-320001, table 165.
     */
    public static final int NO_ZOOM = 8;

    /**
     * Annotation flag.
     * See also {@link PdfAnnotation#setFlag(int)} and ISO-320001, table 165.
     */
    public static final int NO_ROTATE = 16;

    /**
     * Annotation flag.
     * See also {@link PdfAnnotation#setFlag(int)} and ISO-320001, table 165.
     */
    public static final int NO_VIEW = 32;

    /**
     * Annotation flag.
     * See also {@link PdfAnnotation#setFlag(int)} and ISO-320001, table 165.
     */
    public static final int READ_ONLY = 64;

    /**
     * Annotation flag.
     * See also {@link PdfAnnotation#setFlag(int)} and ISO-320001, table 165.
     */
    public static final int LOCKED = 128;

    /**
     * Annotation flag.
     * See also {@link PdfAnnotation#setFlag(int)} and ISO-320001, table 165.
     */
    public static final int TOGGLE_NO_VIEW = 256;

    /**
     * Annotation flag.
     * See also {@link PdfAnnotation#setFlag(int)} and ISO-320001, table 165.
     */
    public static final int LOCKED_CONTENTS = 512;


    /**
     * Widget annotation highlighting mode. See ISO-320001, Table 188 (H key).
     * Also see {@link PdfWidgetAnnotation#setHighlightMode(PdfName)}.
     */
    public static final PdfName HIGHLIGHT_NONE = PdfName.N;

    /**
     * Widget annotation highlighting mode. See ISO-320001, Table 188 (H key).
     * Also see {@link PdfWidgetAnnotation#setHighlightMode(PdfName)}.
     */
    public static final PdfName HIGHLIGHT_INVERT = PdfName.I;

    /**
     * Widget annotation highlighting mode. See ISO-320001, Table 188 (H key).
     * Also see {@link PdfWidgetAnnotation#setHighlightMode(PdfName)}.
     */
    public static final PdfName HIGHLIGHT_OUTLINE = PdfName.O;

    /**
     * Widget annotation highlighting mode. See ISO-320001, Table 188 (H key).
     * Also see {@link PdfWidgetAnnotation#setHighlightMode(PdfName)}.
     */
    public static final PdfName HIGHLIGHT_PUSH = PdfName.P;

    /**
     * Widget annotation highlighting mode. See ISO-320001, Table 188 (H key).
     * Also see {@link PdfWidgetAnnotation#setHighlightMode(PdfName)}.
     */
    public static final PdfName HIGHLIGHT_TOGGLE = PdfName.T;


    /**
     * Annotation border style. See ISO-320001, Table 166 (S key).
     * Also see {@link PdfAnnotation#setBorderStyle(PdfName)}
     */
    public static final PdfName STYLE_SOLID = PdfName.S;

    /**
     * Annotation border style. See ISO-320001, Table 166 (S key).
     * Also see {@link PdfAnnotation#setBorderStyle(PdfName)}
     */
    public static final PdfName STYLE_DASHED = PdfName.D;

    /**
     * Annotation border style. See ISO-320001, Table 166 (S key).
     * Also see {@link PdfAnnotation#setBorderStyle(PdfName)}
     */
    public static final PdfName STYLE_BEVELED = PdfName.B;

    /**
     * Annotation border style. See ISO-320001, Table 166 (S key).
     * Also see {@link PdfAnnotation#setBorderStyle(PdfName)}
     */
    public static final PdfName STYLE_INSET = PdfName.I;

    /**
     * Annotation border style. See ISO-320001, Table 166 (S key).
     * Also see {@link PdfAnnotation#setBorderStyle(PdfName)}
     */
    public static final PdfName STYLE_UNDERLINE = PdfName.U;


    /**
     * Annotation state. See ISO-320001 12.5.6.3 "Annotation States" and Table 171 in particular.
     * Also see {@link PdfTextAnnotation#setState(PdfString)}.
     */
    public static final PdfString Marked = new PdfString("Marked");

    /**
     * Annotation state. See ISO-320001 12.5.6.3 "Annotation States" and Table 171 in particular.
     * Also see {@link PdfTextAnnotation#setState(PdfString)}.
     */
    public static final PdfString Unmarked = new PdfString("Unmarked");

    /**
     * Annotation state. See ISO-320001 12.5.6.3 "Annotation States" and Table 171 in particular.
     * Also see {@link PdfTextAnnotation#setState(PdfString)}.
     */
    public static final PdfString Accepted = new PdfString("Accepted");

    /**
     * Annotation state. See ISO-320001 12.5.6.3 "Annotation States" and Table 171 in particular.
     * Also see {@link PdfTextAnnotation#setState(PdfString)}.
     */
    public static final PdfString Rejected = new PdfString("Rejected");

    /**
     * Annotation state. See ISO-320001 12.5.6.3 "Annotation States" and Table 171 in particular.
     * Also see {@link PdfTextAnnotation#setState(PdfString)}.
     */
    public static final PdfString Canceled = new PdfString("Cancelled");

    /**
     * Annotation state. See ISO-320001 12.5.6.3 "Annotation States" and Table 171 in particular.
     * Also see {@link PdfTextAnnotation#setState(PdfString)}.
     */
    public static final PdfString Completed = new PdfString("Completed");

    /**
     * Annotation state. See ISO-320001 12.5.6.3 "Annotation States" and Table 171 in particular.
     * Also see {@link PdfTextAnnotation#setState(PdfString)}.
     */
    public static final PdfString None = new PdfString("None");


    /**
     * Annotation state model. See ISO-320001, Table 172 (StateModel key).
     * Also see {@link PdfTextAnnotation#setStateModel(PdfString)}.
     */
    public static final PdfString MarkedModel = new PdfString("Marked");

    /**
     * Annotation state model. See ISO-320001, Table 172 (StateModel key).
     * Also see {@link PdfTextAnnotation#setStateModel(PdfString)}.
     */
    public static final PdfString ReviewModel = new PdfString("Review");

    protected PdfPage page;

    /**
     * Factory method that creates the type specific {@link PdfAnnotation} from the given {@link PdfObject}
     * that represents annotation object. This method is useful for property reading in reading mode or
     * modifying in stamping mode. See derived classes of this class to see possible specific annotation types
     * created.
     * @param pdfObject a {@link PdfObject} that represents annotation in the document.
     * @return created {@link PdfAnnotation}.
     */
    public static PdfAnnotation makeAnnotation(PdfObject pdfObject) {
        PdfAnnotation annotation = null;
        if (pdfObject.isIndirectReference())
            pdfObject = ((PdfIndirectReference) pdfObject).getRefersTo();
        if (pdfObject.isDictionary()) {
            PdfDictionary dictionary = (PdfDictionary) pdfObject;
            PdfName subtype = dictionary.getAsName(PdfName.Subtype);
            if (PdfName.Link.equals(subtype)) {
                annotation = new PdfLinkAnnotation((PdfDictionary) pdfObject);
            } else if (PdfName.Popup.equals(subtype)) {
                annotation = new PdfPopupAnnotation((PdfDictionary) pdfObject);
            } else if (PdfName.Widget.equals(subtype)) {
                annotation = new PdfWidgetAnnotation((PdfDictionary) pdfObject);
            } else if (PdfName.Screen.equals(subtype)) {
                annotation = new PdfScreenAnnotation((PdfDictionary) pdfObject);
            } else if (PdfName._3D.equals(subtype)) {
                throw new UnsupportedOperationException();
            } else if (PdfName.Highlight.equals(subtype) || PdfName.Underline.equals(subtype) || PdfName.Squiggly.equals(subtype) || PdfName.StrikeOut.equals(subtype)) {
                annotation = new PdfTextMarkupAnnotation((PdfDictionary) pdfObject);
            } else if (PdfName.Caret.equals(subtype)) {
                annotation = new PdfCaretAnnotation((PdfDictionary) pdfObject);
            } else if (PdfName.Text.equals(subtype)) {
                annotation = new PdfTextAnnotation((PdfDictionary) pdfObject);
            } else if (PdfName.Sound.equals(subtype)) {
                annotation = new PdfSoundAnnotation((PdfDictionary) pdfObject);
            } else if (PdfName.Stamp.equals(subtype)) {
                annotation = new PdfStampAnnotation((PdfDictionary) pdfObject);
            } else if (PdfName.FileAttachment.equals(subtype)) {
                annotation = new PdfFileAttachmentAnnotation((PdfDictionary) pdfObject);
            } else if (PdfName.Ink.equals(subtype)) {
                annotation = new PdfInkAnnotation((PdfDictionary) pdfObject);
            } else if (PdfName.PrinterMark.equals(subtype)) {
                annotation = new PdfPrinterMarkAnnotation((PdfDictionary) pdfObject);
            } else if (PdfName.TrapNet.equals(subtype)) {
                annotation = new PdfTrapNetworkAnnotation((PdfDictionary) pdfObject);
            } else if (PdfName.FreeText.equals(subtype)) {
                annotation = new PdfFreeTextAnnotation((PdfDictionary) pdfObject);
            } else if (PdfName.Square.equals(subtype)) {
                annotation = new PdfSquareAnnotation((PdfDictionary) pdfObject);
            } else if (PdfName.Circle.equals(subtype)) {
                annotation = new PdfCircleAnnotation((PdfDictionary) pdfObject);
            } else if (PdfName.Line.equals(subtype)) {
                annotation = new PdfLineAnnotation((PdfDictionary) pdfObject);
            } else if (PdfName.Polygon.equals(subtype) || PdfName.PolyLine.equals(subtype)) {
                annotation = new PdfPolyGeomAnnotation((PdfDictionary) pdfObject);
            } else if (PdfName.Redact.equals(subtype)) {
                annotation = new PdfRedactAnnotation((PdfDictionary) pdfObject);
            } else if (PdfName.Watermark.equals(subtype)) {
                annotation = new PdfWatermarkAnnotation((PdfDictionary) pdfObject);
            }
        }
        return annotation;
    }

    /**
     * Factory method that creates the type specific {@link PdfAnnotation} from the given {@link PdfObject}
     * that represents annotation object. This method is useful for property reading in reading mode or
     * modifying in stamping mode.
     * @param pdfObject a {@link PdfObject} that represents annotation in the document.
     * @param parent parent annotation of the {@link PdfPopupAnnotation} to be created. This parameter is
     *               only needed if passed {@link PdfObject} represents a pop-up annotation in the document.
     * @return created {@link PdfAnnotation}.
     * @deprecated This method will be removed in iText 7.1. Please, simply use {@link PdfAnnotation#makeAnnotation(PdfObject)}.
     */
    @Deprecated
    public static PdfAnnotation makeAnnotation(PdfObject pdfObject, PdfAnnotation parent) {
        PdfAnnotation annotation = makeAnnotation(pdfObject);
        if (annotation instanceof PdfPopupAnnotation) {
            PdfPopupAnnotation popup = (PdfPopupAnnotation) annotation;
            if (parent != null)
                popup.setParent(parent);
        }

        return annotation;
    }

    protected PdfAnnotation(Rectangle rect) {
        this(new PdfDictionary());
        put(PdfName.Rect, new PdfArray(rect));
        put(PdfName.Subtype, getSubtype());
    }

    protected PdfAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
        markObjectAsIndirect(getPdfObject());
    }

    /**
     * Gets a {@link PdfName} which value is a subtype of this annotation.
     * See ISO-320001 12.5.6, “Annotation Types” for the reference to the possible types.
     * @return subtype of this annotation.
     */
    public abstract PdfName getSubtype();

    /**
     * Sets the layer this annotation belongs to.
     *
     * @param layer the layer this annotation belongs to
     */
    public void setLayer(IPdfOCG layer) {
        getPdfObject().put(PdfName.OC, layer.getIndirectReference());
    }

    /**
     * Sets a {@link PdfAction} to this annotation which will be performed when the annotation is activated.
     * @param action {@link PdfAction} to set to this annotation.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setAction(PdfAction action) {
        return put(PdfName.A, action.getPdfObject());
    }

    /**
     * Sets an additional {@link PdfAction} to this annotation which will be performed in response to
     * the specific trigger event defined by {@code key}. See ISO-320001 12.6.3, "Trigger Events".
     * @param key a {@link PdfName} that denotes a type of the additional action to set.
     * @param action {@link PdfAction} to set as additional to this annotation.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setAdditionalAction(PdfName key, PdfAction action) {
        PdfAction.setAdditionalAction(this, key, action);
        return this;
    }

    /**
     * Gets the text that shall be displayed for the annotation or, if this type of annotation does not display text,
     * an alternate description of the annotation’s contents in human-readable form.
     * @return annotation text content.
     */
    public PdfString getContents() {
        return getPdfObject().getAsString(PdfName.Contents);
    }

    /**
     * Sets the text that shall be displayed for the annotation or, if this type of annotation does not display text,
     * an alternate description of the annotation’s contents in human-readable form.
     * @param contents a {@link PdfString} containing text content to be set to the annotation.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setContents(PdfString contents) {
        return put(PdfName.Contents, contents);
    }

    /**
     * Sets the text that shall be displayed for the annotation or, if this type of annotation does not display text,
     * an alternate description of the annotation’s contents in human-readable form.
     * @param contents a java {@link String} containing text content to be set to the annotation.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setContents(String contents) {
        return setContents(new PdfString(contents, PdfEncodings.UNICODE_BIG));
    }

    /**
     * Gets a {@link PdfDictionary} that represents a page of the document on which annotation is placed,
     * i.e. which has this annotation in it's /Annots array.
     * @return {@link PdfDictionary} that is a page pdf object or null if annotation is not added to the page yet.
     */
    public PdfDictionary getPageObject() {
        return getPdfObject().getAsDictionary(PdfName.P);
    }

    /**
     * Gets a {@link PdfPage} on which annotation is placed.
     * @return {@link PdfPage} on which annotation is placed or null if annotation is not placed yet.
     */
    public PdfPage getPage() {
        if (page == null && getPdfObject().isIndirect()) {
            PdfIndirectReference annotationIndirectReference = getPdfObject().getIndirectReference();
            PdfDocument doc = annotationIndirectReference.getDocument();

            PdfDictionary pageDictionary = getPageObject();
            if (pageDictionary != null) {
                page = doc.getPage(pageDictionary);
            } else {
                for (int i = 1; i <= doc.getNumberOfPages(); i++) {
                    PdfPage docPage = doc.getPage(i);
                    for (PdfAnnotation annot : docPage.getAnnotations()) {
                        if (annot.getPdfObject().getIndirectReference().equals(annotationIndirectReference)) {
                            page = docPage;
                            break;
                        }
                    }
                }
            }


        }
        return page;
    }

    /**
     * Method that modifies annotation page property, which defines to which page annotation belongs.
     * Keep in mind that this doesn't actually add an annotation to the page,
     * it should be done via {@link PdfPage#addAnnotation(PdfAnnotation)}.
     * Also you don't need to set this property manually, this is done automatically on addition to the page.
     * @param page the {@link PdfPage} to which annotation will be added.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setPage(PdfPage page) {
        this.page = page;
        return put(PdfName.P, page.getPdfObject());
    }

    /**
     * Gets the annotation name, a text string uniquely identifying it among all the
     * annotations on its page.
     * @return a {@link PdfString} with annotation name as it's value or null if name
     * is not specified.
     */
    public PdfString getName() {
        return getPdfObject().getAsString(PdfName.NM);
    }

    /**
     * Sets the annotation name, a text string uniquely identifying it among all the
     * annotations on its page.
     * @param name a {@link PdfString} to be set as annotation name.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setName(PdfString name) {
        return put(PdfName.NM, name);
    }

    /**
     * The date and time when the annotation was most recently modified.
     * This is an optional property of the annotation.
     * @return a {@link PdfString} with the modification date as it's value or null if date is not specified.
     */
    public PdfString getDate() {
        return getPdfObject().getAsString(PdfName.M);
    }

    /**
     * The date and time when the annotation was most recently modified.
     * @param date a {@link PdfString} with date. The format should be a date string as described
     *             in ISO-320001 7.9.4, “Dates”.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setDate(PdfString date) {
        return put(PdfName.M, date);
    }

    /**
     * A set of flags specifying various characteristics of the annotation (see ISO-320001 12.5.3, “Annotation Flags”).
     * For specific annotation flag constants see {@link PdfAnnotation#setFlag(int)}.
     * Default value: 0.
     * @return an integer interpreted as one-bit flags specifying various characteristics of the annotation.
     */
    public int getFlags() {
        PdfNumber f = getPdfObject().getAsNumber(PdfName.F);
        if (f != null)
            return f.intValue();
        else
            return 0;
    }

    /**
     * Sets a set of flags specifying various characteristics of the annotation (see ISO-320001 12.5.3, “Annotation Flags”).
     * On the contrary from {@link PdfAnnotation#setFlag(int)}, this method sets a complete set of enabled and disabled flags at once.
     * If not set specifically the default value is 0.
     * @param flags an integer interpreted as set of one-bit flags specifying various characteristics of the annotation.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setFlags(int flags) {
        return put(PdfName.F, new PdfNumber(flags));
    }

    /**
     * Sets a flag that specifies a characteristic of the annotation to enabled state (see ISO-320001 12.5.3, “Annotation Flags”).
     * On the contrary from {@link PdfAnnotation#setFlags(int)}, this method sets only specified flags to enabled state,
     * but doesn't disable other flags.
     * Possible flags:
     * <ul>
     *     <li>{@link PdfAnnotation#INVISIBLE} - If set, do not display the annotation if it does not belong to one of the
     *     standard annotation types and no annotation handler is available. If clear, display such unknown annotation
     *     using an appearance stream specified by its appearance dictionary, if any.
     *     </li>
     *     <li>{@link PdfAnnotation#HIDDEN} - If set, do not display or print the annotation or allow it to interact with
     *     the user, regardless of its annotation type or whether an annotation handler is available.
     *     </li>
     *     <li>{@link PdfAnnotation#PRINT} - If set, print the annotation when the page is printed. If clear, never print
     *     the annotation, regardless of whether it is displayed on the screen.
     *     </li>
     *     <li>{@link PdfAnnotation#NO_ZOOM} - If set, do not scale the annotation’s appearance to match the magnification of
     *     the page. The location of the annotation on the page (defined by the upper-left corner of its annotation
     *     rectangle) shall remain fixed, regardless of the page magnification.}
     *     </li>
     *     <li>{@link PdfAnnotation#NO_ROTATE} - If set, do not rotate the annotation’s appearance to match the rotation
     *     of the page. The upper-left corner of the annotation rectangle shall remain in a fixed location on the page,
     *     regardless of the page rotation.
     *     </li>
     *     <li>{@link PdfAnnotation#NO_VIEW} - If set, do not display the annotation on the screen or allow it to interact
     *     with the user. The annotation may be printed (depending on the setting of the Print flag) but should be considered
     *     hidden for purposes of on-screen display and user interaction.
     *     </li>
     *     <li>{@link PdfAnnotation#READ_ONLY} -  If set, do not allow the annotation to interact with the user. The annotation
     *     may be displayed or printed (depending on the settings of the NoView and Print flags) but should not respond to mouse
     *     clicks or change its appearance in response to mouse motions.
     *     </li>
     *     <li>{@link PdfAnnotation#LOCKED} -  If set, do not allow the annotation to be deleted or its properties
     *     (including position and size) to be modified by the user. However, this flag does not restrict changes to
     *     the annotation’s contents, such as the value of a form field.
     *     </li>
     *     <li>{@link PdfAnnotation#TOGGLE_NO_VIEW} - If set, invert the interpretation of the NoView flag for certain events.
     *     </li>
     *     <li>{@link PdfAnnotation#LOCKED_CONTENTS} - If set, do not allow the contents of the annotation to be modified
     *     by the user. This flag does not restrict deletion of the annotation or changes to other annotation properties,
     *     such as position and size.
     *     </li>
     * </ul>
     * @param flag - an integer interpreted as set of one-bit flags which will be enabled for this annotation.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setFlag(int flag) {
        int flags = getFlags();
        flags = flags | flag;
        return setFlags(flags);
    }

    /**
     * Resets a flag that specifies a characteristic of the annotation to disabled state (see ISO-320001 12.5.3, “Annotation Flags”).
     * @param flag an integer interpreted as set of one-bit flags which will be reset to disabled state.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation resetFlag(int flag) {
        int flags = getFlags();
        flags = flags & ~flag;
        return setFlags(flags);
    }

    /**
     * Checks if the certain flag that specifies a characteristic of the annotation
     * is in enabled state (see ISO-320001 12.5.3, “Annotation Flags”).
     * This method allows only one flag to be checked at once, use constants listed in {@link PdfAnnotation#setFlag(int)}.
     * @param flag an integer interpreted as set of one-bit flags. Only one bit must be set in this integer, otherwise
     *             exception is thrown.
     * @return true if the given flag is in enabled state.
     */
    public boolean hasFlag(int flag) {
        if (flag == 0) {
            return false;
        }
        if ((flag & flag-1) != 0) {
            throw new IllegalArgumentException("Only one flag must be checked at once.");
        }

        int flags = getFlags();
        return (flags & flag) != 0;
    }

    /**
     * An appearance dictionary specifying how the annotation shall be presented visually on the page during its
     * interactions with the user (see ISO-320001 12.5.5, “Appearance Streams”). An appearance dictionary is a dictionary
     * containing one or several appearance streams or subdictionaries.
     * @return an appearance {@link PdfDictionary} or null if it is not specified.
     */
    public PdfDictionary getAppearanceDictionary() {
        return getPdfObject().getAsDictionary(PdfName.AP);
    }

    /**
     * Specific appearance object corresponding to the specific appearance type. This object might be either an appearance
     * stream or an appearance subdictionary. In the latter case, the subdictionary defines multiple appearance streams
     * corresponding to different appearance states of the annotation. See ISO-320001 12.5.5, “Appearance Streams”.
     * @param appearanceType a {@link PdfName} specifying appearance type. Possible types are {@link PdfName#N Normal},
     *                       {@link PdfName#R Rollover} and {@link PdfName#D Down}.
     * @return null if their is no such appearance type or an appearance object which might be either
     * an appearance stream or an appearance subdictionary.
     */
    public PdfDictionary getAppearanceObject(PdfName appearanceType) {
        PdfDictionary ap = getAppearanceDictionary();
        if (ap != null) {
            PdfObject apObject = ap.get(appearanceType);
            if (apObject instanceof PdfDictionary) {
                return (PdfDictionary) apObject;
            }
        }
        return null;
    }

    /**
     * The normal appearance is used when the annotation is not interacting with the user.
     * This appearance is also used for printing the annotation.
     * See also {@link PdfAnnotation#getAppearanceObject(PdfName)}.
     * @return an appearance object which might be either an appearance stream or an appearance subdictionary.
     */
    public PdfDictionary getNormalAppearanceObject() {
        return getAppearanceObject(PdfName.N);
    }

    /**
     * The rollover appearance is used when the user moves the cursor into the annotation’s active area
     * without pressing the mouse button. If not specified normal appearance is used.
     * See also {@link PdfAnnotation#getAppearanceObject(PdfName)}.
     * @return null if rollover appearance is not specified or an appearance object which might be either
     * an appearance stream or an appearance subdictionary.
     */
    public PdfDictionary getRolloverAppearanceObject() {
        return getAppearanceObject(PdfName.R);
    }

    /**
     * The down appearance is used when the mouse button is pressed or held down within the annotation’s active area.
     * If not specified normal appearance is used.
     * See also {@link PdfAnnotation#getAppearanceObject(PdfName)}.
     * @return null if down appearance is not specified or an appearance object which might be either
     * an appearance stream or an appearance subdictionary.
     */
    public PdfDictionary getDownAppearanceObject() {
        return getAppearanceObject(PdfName.D);
    }

    /**
     * Sets a specific type of the appearance. See {@link PdfAnnotation#getAppearanceObject(PdfName)} and
     * {@link PdfAnnotation#getAppearanceDictionary()} for more info.
     * @param appearanceType a {@link PdfName} specifying appearance type. Possible types are {@link PdfName#N Normal},
     *                       {@link PdfName#R Rollover} and {@link PdfName#D Down}.
     * @param appearance an appearance object which might be either an appearance stream or an appearance subdictionary.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setAppearance(PdfName appearanceType, PdfDictionary appearance) {
        PdfDictionary ap = getAppearanceDictionary();
        if (ap == null) {
            ap = new PdfDictionary();
            getPdfObject().put(PdfName.AP, ap);
        }
        ap.put(appearanceType, appearance);
        return this;
    }

    /**
     * Sets normal appearance. See {@link PdfAnnotation#getNormalAppearanceObject()} and
     * {@link PdfAnnotation#getAppearanceDictionary()} for more info.
     * @param appearance an appearance object which might be either an appearance stream or an appearance subdictionary.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setNormalAppearance(PdfDictionary appearance) {
        return setAppearance(PdfName.N, appearance);
    }

    /**
     * Sets rollover appearance. See {@link PdfAnnotation#getRolloverAppearanceObject()} and
     * {@link PdfAnnotation#getAppearanceDictionary()} for more info.
     * @param appearance an appearance object which might be either an appearance stream or an appearance subdictionary.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setRolloverAppearance(PdfDictionary appearance) {
        return setAppearance(PdfName.R, appearance);
    }

    /**
     * Sets down appearance. See {@link PdfAnnotation#getDownAppearanceObject()} and
     * {@link PdfAnnotation#getAppearanceDictionary()} for more info.
     * @param appearance an appearance object which might be either an appearance stream or an appearance subdictionary.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setDownAppearance(PdfDictionary appearance) {
        return setAppearance(PdfName.D, appearance);
    }

    /**
     * Sets a specific type of the appearance using {@link PdfAnnotationAppearance} wrapper.
     * This method is used to set only an appearance subdictionary. See {@link PdfAnnotation#getAppearanceObject(PdfName)}
     * and {@link PdfAnnotation#getAppearanceDictionary()} for more info.
     * @param appearanceType a {@link PdfName} specifying appearance type. Possible types are {@link PdfName#N Normal},
     *                       {@link PdfName#R Rollover} and {@link PdfName#D Down}.
     * @param appearance an appearance subdictionary wrapped in {@link PdfAnnotationAppearance}.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setAppearance(PdfName appearanceType, PdfAnnotationAppearance appearance) {
        return setAppearance(appearanceType, appearance.getPdfObject());
    }

    /**
     * Sets normal appearance using {@link PdfAnnotationAppearance} wrapper. This method is used to set only
     * appearance subdictionary. See {@link PdfAnnotation#getNormalAppearanceObject()} and
     * {@link PdfAnnotation#getAppearanceDictionary()} for more info.
     * @param appearance an appearance subdictionary wrapped in {@link PdfAnnotationAppearance}.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setNormalAppearance(PdfAnnotationAppearance appearance) {
        return setAppearance(PdfName.N, appearance);
    }

    /**
     * Sets rollover appearance using {@link PdfAnnotationAppearance} wrapper. This method is used to set only
     * appearance subdictionary. See {@link PdfAnnotation#getRolloverAppearanceObject()} and
     * {@link PdfAnnotation#getAppearanceDictionary()} for more info.
     * @param appearance an appearance subdictionary wrapped in {@link PdfAnnotationAppearance}.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setRolloverAppearance(PdfAnnotationAppearance appearance) {
        return setAppearance(PdfName.R, appearance);
    }

    /**
     * Sets down appearance using {@link PdfAnnotationAppearance} wrapper. This method is used to set only
     * appearance subdictionary. See {@link PdfAnnotation#getDownAppearanceObject()} and
     * {@link PdfAnnotation#getAppearanceDictionary()} for more info.
     * @param appearance an appearance subdictionary wrapped in {@link PdfAnnotationAppearance}.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setDownAppearance(PdfAnnotationAppearance appearance) {
        return setAppearance(PdfName.D, appearance);
    }

    /**
     * The annotation’s appearance state, which selects the applicable appearance stream
     * from an appearance subdictionary if there is such. See {@link PdfAnnotation#getAppearanceObject(PdfName)}
     * for more info.
     * @return a {@link PdfName} which defines selected appearance state.
     */
    public PdfName getAppearanceState() {
        return getPdfObject().getAsName(PdfName.AS);
    }

    /**
     * Sets the annotation’s appearance state, which selects the applicable appearance stream
     * from an appearance subdictionary. See {@link PdfAnnotation#getAppearanceObject(PdfName)}
     * for more info.
     * @param as a {@link PdfName} which defines appearance state to be selected.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setAppearanceState(PdfName as) {
        return put(PdfName.AS, as);
    }

    /**
     * <p>
     * An array specifying the characteristics of the annotation’s border.
     * The array consists of three numbers defining the horizontal corner radius,
     * vertical corner radius, and border width, all in default user space units.
     * If the corner radii are 0, the border has square (not rounded) corners; if
     * the border width is 0, no border is drawn.
     * <p>
     * The array may have a fourth element, an optional dash array (see ISO-320001 8.4.3.6, “Line Dash Pattern”).
     * @return an {@link PdfArray} specifying the characteristics of the annotation’s border.
     */
    public PdfArray getBorder() {
        return getPdfObject().getAsArray(PdfName.Border);
    }

    /**
     * Sets the characteristics of the annotation’s border.
     * @param border an {@link PdfArray} specifying the characteristics of the annotation’s border.
     *               See {@link PdfAnnotation#getBorder()} for more detailes.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setBorder(PdfArray border) {
        return put(PdfName.Border, border);
    }

    /**
     * An array of numbers in the range 0.0 to 1.0, representing a colour used for the following purposes:
     * <ul>
     *     <li>The background of the annotation’s icon when closed</li>
     *     <li>The title bar of the annotation’s pop-up window</li>
     *     <li>The border of a link annotation</li>
     * </ul>
     * The number of array elements determines the colour space in which the colour shall be defined:
     * <ul>
     *     <li>0 - No colour; transparent</li>
     *     <li>1 - DeviceGray</li>
     *     <li>3 - DeviceRGB</li>
     *     <li>4 - DeviceCMYK</li>
     * </ul>
     * @return An array of numbers in the range 0.0 to 1.0, representing an annotation colour.
     */
    public PdfArray getColorObject() {
        return getPdfObject().getAsArray(PdfName.C);
    }

    /**
     * Sets an annotation color. For more details on annotation color purposes and the format
     * of the passing {@link PdfArray} see {@link PdfAnnotation#getColorObject()}.
     * @param color an array of numbers in the range 0.0 to 1.0, specifying color.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setColor(PdfArray color) {
        return put(PdfName.C, color);
    }

    /**
     * Sets an annotation color. For more details on annotation color purposes and the format
     * of the passing array see {@link PdfAnnotation#getColorObject()}.
     * @param color an array of numbers in the range 0.0 to 1.0, specifying color.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setColor(float[] color) {
        return setColor(new PdfArray(color));
    }

    /**
     * Sets an annotation color. For more details on annotation color purposes
     * see {@link PdfAnnotation#getColorObject()}.
     * @param color {@link Color} object of the either {@link com.itextpdf.kernel.color.DeviceGray},
     *              {@link com.itextpdf.kernel.color.DeviceRgb} or  {@link com.itextpdf.kernel.color.DeviceCmyk} type.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setColor(Color color) {
        return setColor(new PdfArray(color.getColorValue()));
    }

    /**
     * The integer key of the annotation’s entry in the structural parent tree
     * (see ISO-320001 14.7.4.4, “Finding Structure Elements from Content Items”).
     * @return integer key in structural parent tree or -1 if annotation is not tagged.
     */
    public int getStructParentIndex() {
        PdfNumber n = getPdfObject().getAsNumber(PdfName.StructParent);
        if (n == null)
            return -1;
        else
            return n.intValue();
    }

    /**
     * Sets he integer key of the annotation’s entry in the structural parent tree
     * (see ISO-320001 14.7.4.4, “Finding Structure Elements from Content Items”).
     * Note: Normally, there is no need to take care of this manually, struct parent index is set automatically
     * if annotation is added to the tagged document's page.
     * @param structParentIndex integer which is to be the key of the annotation's entry
     *                          in structural parent tree.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setStructParentIndex(int structParentIndex) {
        return put(PdfName.StructParent, new PdfNumber(structParentIndex));
    }

    /**
     * A flag specifying whether the annotation shall initially be displayed open.
     * This flag has affect to not all kinds of annotations.
     * @return true if annotation is initially open, false - if closed.
     */
    public boolean getOpen() {
        PdfBoolean open = getPdfObject().getAsBoolean(PdfName.Open);
        return open != null && open.getValue();
    }

    /**
     * Sets a flag specifying whether the annotation shall initially be displayed open.
     * This flag has affect to not all kinds of annotations.
     * @param open true if annotation shall initially be open, false - if closed.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setOpen(boolean open) {
        return put(PdfName.Open, PdfBoolean.valueOf(open));
    }

    /**
     * An array of 8 × n numbers specifying the coordinates of n quadrilaterals in default user space.
     * Quadrilaterals are used to define:
     * <ul>
     * <li>regions inside annotation rectangle in which the link annotation should be activated;</li>
     * <li>a word or group of contiguous words in the text underlying the text markup annotation;</li>
     * <li>the content region that is intended to be removed for a redaction annotation;</li>
     * </ul>
     *
     * <p>
     * IMPORTANT NOTE: According to Table 179 in ISO 32000-1, the QuadPoints array lists the vertices in counterclockwise
     * order and the text orientation is defined by the first and second vertex. This basically means QuadPoints is
     * specified as lower-left, lower-right, top-right, top-left. HOWEVER, Adobe's interpretation
     * (tested at least with Acrobat 10, Acrobat 11, Reader 11) is top-left, top-right, lower-left, lower-right (Z-shaped order).
     * This means that if the QuadPoints array is specified according to the standard, the rendering is not as expected.
     * Other viewers seem to follow Adobe's interpretation. Hence we recommend to use and expect QuadPoints array in Z-order,
     * just as Acrobat and probably most other viewers expect.
     * @return an {@link PdfArray} of 8 × n numbers specifying the coordinates of n quadrilaterals.
     */
    public PdfArray getQuadPoints() {
        return getPdfObject().getAsArray(PdfName.QuadPoints);
    }

    /**
     * Sets n quadrilaterals in default user space by passing an {@link PdfArray} of 8 × n numbers. For more info of what
     * quadrilaterals define see {@link PdfAnnotation#getQuadPoints()}.
     *
     * <p>
     * IMPORTANT NOTE: According to Table 179 in ISO 32000-1, the QuadPoints array lists the vertices in counterclockwise
     * order and the text orientation is defined by the first and second vertex. This basically means QuadPoints is
     * specified as lower-left, lower-right, top-right, top-left. HOWEVER, Adobe's interpretation
     * (tested at least with Acrobat 10, Acrobat 11, Reader 11) is top-left, top-right, lower-left, lower-right (Z-shaped order).
     * This means that if the QuadPoints array is specified according to the standard, the rendering is not as expected.
     * Other viewers seem to follow Adobe's interpretation. Hence we recommend to use and expect QuadPoints array in Z-order,
     * just as Acrobat and probably most other viewers expect.
     * @param quadPoints an {@link PdfArray} of 8 × n numbers specifying the coordinates of n quadrilaterals.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setQuadPoints(PdfArray quadPoints) {
        return put(PdfName.QuadPoints, quadPoints);
    }

    /**
     * Sets border style dictionary that has more settings than the array specified for the Border entry ({@link PdfAnnotation#getBorder()}).
     * See ISO-320001, Table 166 and {@link PdfAnnotation#getBorderStyle()} for more info.
     * @param borderStyle a border style dictionary specifying the line width and dash pattern that shall be used
     *                    in drawing the annotation’s border.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setBorderStyle(PdfDictionary borderStyle) {
        return put(PdfName.BS, borderStyle);
    }

    /**
     * Setter for the annotation's preset border style. Possible values are
     * <ul>
     *     <li>{@link PdfAnnotation#STYLE_SOLID} - A solid rectangle surrounding the annotation.</li>
     *     <li>{@link PdfAnnotation#STYLE_DASHED} - A dashed rectangle surrounding the annotation.</li>
     *     <li>{@link PdfAnnotation#STYLE_BEVELED} - A simulated embossed rectangle that appears to be raised above the surface of the page.</li>
     *     <li>{@link PdfAnnotation#STYLE_INSET} - A simulated engraved rectangle that appears to be recessed below the surface of the page.</li>
     *     <li>{@link PdfAnnotation#STYLE_UNDERLINE} - A single line along the bottom of the annotation rectangle.</li>
     * </ul>
     * See also ISO-320001, Table 166.
     * @param style The new value for the annotation's border style.
     * @return The annotation which this method was called on.
     * @see PdfAnnotation#getBorderStyle()
     */
    public PdfAnnotation setBorderStyle(PdfName style) {
        PdfDictionary styleDict = getBorderStyle();
        if (null == styleDict) {
            styleDict = new PdfDictionary();
        }
        styleDict.put(PdfName.S, style);
        return setBorderStyle(styleDict);
    }

    /**
     * Setter for the annotation's preset dashed border style. This property has affect only if {@link PdfAnnotation#STYLE_DASHED}
     * style was used for the annotation border style (see {@link PdfAnnotation#setBorderStyle(PdfName)}.
     * See ISO-320001 8.4.3.6, “Line Dash Pattern” for the format in which dash pattern shall be specified.
     * @param dashPattern a dash array defining a pattern of dashes and gaps that
     *                    shall be used in drawing a dashed border.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setDashPattern(PdfArray dashPattern) {
        PdfDictionary styleDict = getBorderStyle();
        if (null == styleDict) {
            styleDict = new PdfDictionary();
        }
        styleDict.put(PdfName.D, dashPattern);
        return setBorderStyle(styleDict);
    }

    /**
     * The dictionaries for some annotation types (such as free text and polygon annotations) can include the BS entry.
     * That entry specifies a border style dictionary that has more settings than the array specified for the Border
     * entry (see {@link PdfAnnotation#getBorder()}). If an annotation dictionary includes the BS entry, then the Border
     * entry is ignored. If annotation includes AP (see {@link PdfAnnotation#getAppearanceDictionary()}) it takes
     * precedence over the BS entry. For more info on BS entry see ISO-320001, Table 166.
     * @return {@link PdfDictionary} which is a border style dictionary or null if it is not specified.
     */
    public PdfDictionary getBorderStyle() {
        return getPdfObject().getAsDictionary(PdfName.BS);
    }

    /**
     * Sets annotation title. This property affects not all annotation types.
     * @param title a {@link PdfString} which value is to be annotation title.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setTitle(PdfString title) {
        return put(PdfName.T, title);
    }

    /**
     * Annotation title. For example for markup annotations, the title is the text label that shall be displayed in the
     * title bar of the annotation’s pop-up window when open and active. For movie annotation Movie actions
     * (ISO-320001 12.6.4.9, “Movie Actions”) may use this title to reference the movie annotation.
     * @return {@link PdfString} which value is an annotation title or null if it isn't specifed.
     */
    public PdfString getTitle() {
        return getPdfObject().getAsString(PdfName.T);
    }

    /**
     * Sets an appearance characteristics dictionary containing additional information for constructing the
     * annotation’s appearance stream. See ISO-320001, Table 189.
     * This property affects {@link PdfWidgetAnnotation} and {@link PdfScreenAnnotation}.
     * @param characteristics the {@link PdfDictionary} with additional information for appearance stream.
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setAppearanceCharacteristics(PdfDictionary characteristics) {
        return put(PdfName.MK, characteristics);
    }

    /**
     * An appearance characteristics dictionary containing additional information for constructing the
     * annotation’s appearance stream. See ISO-320001, Table 189.
     * This property affects {@link PdfWidgetAnnotation} and {@link PdfScreenAnnotation}.
     * @return an appearance characteristics dictionary or null if it isn't specified.
     */
    public PdfDictionary getAppearanceCharacteristics() {
        return getPdfObject().getAsDictionary(PdfName.MK);
    }

    /**
     * An {@link PdfAction} to perform, such as launching an application, playing a sound,
     * changing an annotation’s appearance state etc, when the annotation is activated.
     * @return {@link PdfDictionary} which defines the characteristics and behaviour of an action.
     */
    public PdfDictionary getAction() {
        return getPdfObject().getAsDictionary(PdfName.A);
    }

    /**
     * An additional actions dictionary that extends the set of events that can trigger the execution of an action.
     * See ISO-320001 12.6.3 Trigger Events.
     * @return an additional actions {@link PdfDictionary}.
     * @see PdfAnnotation#getAction()
     */
    public PdfDictionary getAdditionalAction() {
        return getPdfObject().getAsDictionary(PdfName.AA);
    }

    /**
     * The annotation rectangle, defining the location of the annotation on the page in default user space units.
     * @param array a {@link PdfArray} which specifies a rectangle by two diagonally opposite corners.
     *              Typically, the array is of form [llx lly urx ury].
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation setRectangle(PdfArray array){
        return put(PdfName.Rect, array);
    }

    /**
     * The annotation rectangle, defining the location of the annotation on the page in default user space units.
     * @return a {@link PdfArray} which specifies a rectangle by two diagonally opposite corners.
     *              Typically, the array is of form [llx lly urx ury].
     */
    public PdfArray getRectangle() {
        return getPdfObject().getAsArray(PdfName.Rect);
    }

    /**
     * Inserts the value into into the underlying {@link PdfDictionary} of this {@link PdfAnnotation} and associates it
     * with the specified key. If the key is already present in this {@link PdfAnnotation}, this method will override
     * the old value with the specified one.
     *
     * @param key key to insert or to override
     * @param value the value to associate with the specified key
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation put(PdfName key, PdfObject value) {
        getPdfObject().put(key, value);
        return this;
    }

    /**
     * Removes the specified key from the underlying {@link PdfDictionary} of this {@link PdfAnnotation}.
     *
     * @param key key to be removed
     * @return this {@link PdfAnnotation} instance.
     */
    public PdfAnnotation remove(PdfName key) {
        getPdfObject().remove(key);
        return this;
    }

    /**
     * To manually flush a {@code PdfObject} behind this wrapper, you have to ensure
     * that this object is added to the document, i.e. it has an indirect reference.
     * Basically this means that before flushing you need to explicitly call {@link #makeIndirect(PdfDocument)}.
     * For example: wrapperInstance.makeIndirect(document).flush();
     * Note that not every wrapper require this, only those that have such warning in documentation.
     */
    @Override
    public void flush() {
        super.flush();
    }

    @Override
    protected boolean isWrappedObjectMustBeIndirect() {
        return true;
    }
}
