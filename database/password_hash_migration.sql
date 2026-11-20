-- Apply to a local copy of database uas3 before using the updated application.
ALTER TABLE `user`
    MODIFY COLUMN `password` VARCHAR(255) NOT NULL;
