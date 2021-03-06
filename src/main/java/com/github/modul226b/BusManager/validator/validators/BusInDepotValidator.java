package com.github.modul226b.BusManager.validator.validators;

import com.github.modul226b.BusManager.manager.BusManager;
import com.github.modul226b.BusManager.manager.DataManager;
import com.github.modul226b.BusManager.manager.TripManager;
import com.github.modul226b.BusManager.model.Bus;
import com.github.modul226b.BusManager.model.IValidatable;
import com.github.modul226b.BusManager.validator.internal.ValidationResult;
import com.github.modul226b.BusManager.validator.internal.ValidationState;
import com.github.modul226b.BusManager.validator.internal.AbstractValidator;

/**
 * Checks if a bus is in a Station.
 */
public class BusInDepotValidator extends AbstractValidator<Bus> {
    public BusInDepotValidator(DataManager dataManager, BusManager busManager, TripManager tripManager) {
        super(dataManager, busManager, tripManager);
    }

    @Override
    public ValidationResult validate(Bus validation) {
        if (this.getBusManager().getDepotStation(validation) == null) {
            return ValidationResult.create(ValidationState.ERROR, "Bus " + validation.getName() + " muss in einem Depot sein.");
        }else {
            return ValidationResult.create(ValidationState.SUCCESS, "");
        }
    }

    @Override
    public Class<? extends IValidatable> getType() {
        return Bus.class;
    }
}
