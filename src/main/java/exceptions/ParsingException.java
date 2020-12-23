/*
 *
 *  * Copyright (c) 2020. Bionic 8 Analytics Ltd.
 *  * The Software and accompanying documentation is owned by Bionic 8 Analytics Ltd (Bionic). Bionic reserves all rights in and to the Software and documentation.
 *  * You may not use, copy, modify, distribute or make any other disposition in the software or documentation without the express written permission and subject to the terms of a written license from Bionic.
 *  * BIONIC SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE AND DOCUMENTATION, IF ANY, PROVIDED HEREUNDER IS PROVIDED "AS IS". UNLESS AGREED IN A WRITTEN LICENSE AGREEMENT, BIONIC HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 *  * IN NO EVENT SHALL BIONIC BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF BIONIC HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package exceptions;

/**
 * Exception thrown when error reading message from queue
 */
public class ParsingException extends RuntimeException {
    public ParsingException(String errorMessage, Exception ex) {
        super(errorMessage, ex);
    }

    public ParsingException(String errorMessage) {
        super(errorMessage);
    }
}
