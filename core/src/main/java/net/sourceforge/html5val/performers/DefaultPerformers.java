package net.sourceforge.html5val.performers;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;

import net.sourceforge.html5val.performers.regexp_composer.DigitsRegexpComposer;
import net.sourceforge.html5val.performers.regexp_composer.LengthRegexpComposer;
import net.sourceforge.html5val.performers.regexp_composer.URLRegexpComposer;

public enum DefaultPerformers implements IValidationPerformer {
    DIGITS {
        @Override
        public Class<Digits> getConstraintClass() {
            return Digits.class;
        }

        @Override
        public IProcessableElementTag toValidationTag(Annotation digits, ITemplateContext context,
                IProcessableElementTag elementTag) {
            // The annotated element must be a number with certain length in integer and decimal parts.
            String pattern = DigitsRegexpComposer.forDigits((Digits) digits).regexp();
            final IModelFactory modelFactory = context.getModelFactory();
            IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "pattern", pattern);
            return modifiedTag;
        }
    },
    EMAIL {
        @Override
        public Class<Email> getConstraintClass() {
            return Email.class;
        }

        @Override
        public IProcessableElementTag toValidationTag(Annotation constraint, ITemplateContext context,
                IProcessableElementTag elementTag) {
            final IModelFactory modelFactory = context.getModelFactory();
            IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "type", "email");
            return modifiedTag;
        }
    },
    LENGTH {
        @Override
        public Class<Length> getConstraintClass() {
            return Length.class;
        }

        @Override
        public IProcessableElementTag toValidationTag(Annotation constraint, ITemplateContext context,
                IProcessableElementTag elementTag) {
            Length length = (Length) constraint;
            final IModelFactory modelFactory = context.getModelFactory();
            IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "pattern",
                    LengthRegexpComposer.forLength(length).regexp());
            if (length.min() > 0) {
                modifiedTag = modelFactory.setAttribute(modifiedTag, "required", "required");
            }
            if (length.max() > 0 && length.max() < Integer.MAX_VALUE) {
                modifiedTag = modelFactory.setAttribute(modifiedTag, "maxlength", length.max() + "");
            }
            return modifiedTag;
        }
    },
    MAX {
        @Override
        public Class<Max> getConstraintClass() {
            return Max.class;
        }

        @Override
        public IProcessableElementTag toValidationTag(Annotation constraint, ITemplateContext context,
                IProcessableElementTag elementTag) {
            // The annotated element must be a number with value lower or equal to the specified maximum.
            Max max = (Max) constraint;
            final IModelFactory modelFactory = context.getModelFactory();
            IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "type", "number");
            modifiedTag = modelFactory.setAttribute(modifiedTag, "max", Long.toString(max.value()));
            return modifiedTag;
        }
    },
    MIN {
        @Override
        public Class<Min> getConstraintClass() {
            return Min.class;
        }

        @Override
        public IProcessableElementTag toValidationTag(Annotation constraint, ITemplateContext context,
                IProcessableElementTag elementTag) {
            // The annotated element must be a number with value greater or equal to the specified minimum.
            Min min = (Min) constraint;
            final IModelFactory modelFactory = context.getModelFactory();
            IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "type", "number");
            modifiedTag = modelFactory.setAttribute(modifiedTag, "min", Long.toString(min.value()));
            return modifiedTag;
        }
    },
    NOT_BLANK {
        @Override
        public Class<NotBlank> getConstraintClass() {
            return NotBlank.class;
        }

        @Override
        public IProcessableElementTag toValidationTag(Annotation constraint, ITemplateContext context,
                IProcessableElementTag elementTag) {
            final IModelFactory modelFactory = context.getModelFactory();
            IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "required", "required");
            return modifiedTag;
        }
    },
    NOT_EMPTY {
        @Override
        public Class<NotEmpty> getConstraintClass() {
            return NotEmpty.class;
        }

        @Override
        public IProcessableElementTag toValidationTag(Annotation constraint, ITemplateContext context,
                IProcessableElementTag elementTag) {
            return NOT_BLANK.toValidationTag(constraint, context, elementTag);
        }
    },
    NOT_NULL {
        @Override
        public Class<NotNull> getConstraintClass() {
            return NotNull.class;
        }

        @Override
        public IProcessableElementTag toValidationTag(Annotation constraint, ITemplateContext context,
                IProcessableElementTag elementTag) {
            return NOT_BLANK.toValidationTag(constraint, context, elementTag);
        }
    },
    PATTERN {
        private final List<String> ALLOWED_TYPE_ATTRS = Arrays.asList(
                "text", "search", "email", "url", "tel", "password");

        @Override
        public Class<Pattern> getConstraintClass() {
            return Pattern.class;
        }

        @Override
        public IProcessableElementTag toValidationTag(Annotation constraint, ITemplateContext context,
                IProcessableElementTag elementTag) {
            Pattern pattern = (Pattern) constraint;
            final String type = elementTag.getAttributeValue("type");
            if (ALLOWED_TYPE_ATTRS.contains(type)) {
                final IModelFactory modelFactory = context.getModelFactory();
                IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "pattern", pattern.regexp());
                return modifiedTag;
            }
            return elementTag;
        }
    },
    RANGE {
        @Override
        public Class<Range> getConstraintClass() {
            return Range.class;
        }

        @Override
        public IProcessableElementTag toValidationTag(Annotation constraint, ITemplateContext context,
                IProcessableElementTag elementTag) {
            // The annotated element has to be in the appropriate range.
            Range range = (Range) constraint;
            final IModelFactory modelFactory = context.getModelFactory();
            IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "type", "range");
            modifiedTag = modelFactory.setAttribute(modifiedTag, "min", Long.toString(range.min()));
            modifiedTag = modelFactory.setAttribute(modifiedTag, "max", Long.toString(range.max()));
            return modifiedTag;
        }
    },
    SIZE {
        @Override
        public Class<Size> getConstraintClass() {
            return Size.class;
        }

        @Override
        public IProcessableElementTag toValidationTag(Annotation constraint, ITemplateContext context,
                IProcessableElementTag elementTag) {
            Size size = (Size) constraint;
            final IModelFactory modelFactory = context.getModelFactory();
            IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "pattern",
                    LengthRegexpComposer.forSize(size).regexp());
            if (size.min() > 0) {
                modifiedTag = modelFactory.setAttribute(modifiedTag, "required", "required");
            }
            if (size.max() > 0 && size.max() < Integer.MAX_VALUE) {
                modifiedTag = modelFactory.setAttribute(modifiedTag, "maxlength", size.max() + "");
            }
            return modifiedTag;
        }
    },
    URL {
        @Override
        public Class<URL> getConstraintClass() {
            return URL.class;
        }

        @Override
        public IProcessableElementTag toValidationTag(Annotation constraint, ITemplateContext context,
                IProcessableElementTag elementTag) {
            URL url = (URL) constraint;
            final IModelFactory modelFactory = context.getModelFactory();
            IProcessableElementTag modifiedTag = modelFactory.setAttribute(elementTag, "type", "url");
            modifiedTag = modelFactory.setAttribute(modifiedTag, "pattern", URLRegexpComposer.forURL(url).regexp());
            return modifiedTag;
        }
    },
    __NULL_PERFORMER {
        @Override
        public Class<Annotation> getConstraintClass() {
            return null;
        }

        @Override
        public IProcessableElementTag toValidationTag(Annotation constraint, ITemplateContext context,
                IProcessableElementTag elementTag) {
            return null;
        }
    };
}
