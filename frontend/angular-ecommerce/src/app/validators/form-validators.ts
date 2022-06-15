import { FormControl, ValidationErrors } from '@angular/forms';

export class FormValidators {
  // whitespace validation
  static notOnlyWhiteSpace(control: FormControl): ValidationErrors {
    // check if string only contains whitspace
    if (control.value != null && control.value.trim().length === 0) {
      // invalid, return error object
      return { notOnlyWhiteSpace: true };
    }else{
        return null;
    }

  }
}
