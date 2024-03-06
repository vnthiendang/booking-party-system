import * as React from "react";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};

export default function ModalConfirmDeposit({
  open,
  handleClose,
  handleConfirm,
}) {
  const [value, setValue] = React.useState(0);
  return (
    <div>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
            Confirm Deposit
          </Typography>
          <Typography id="modal-modal-description" sx={{ mt: 2 }}>
            {" "}
            Before you finish your booking, please add deposit below.
            <br />
            <input
              type="number"
              value={value}
              style={{
                fontSize: "30px",
              }}
              onChange={(e) => setValue(e.target.value)}
            />
          </Typography>

          <div
            style={{
              display: "flex",
              alignItems: "center",
              marginTop: "10px",
            }}
          >
            <Button
              variant="contained"
              sx={{ background: "red", marginRight: "16px" }}
              onClick={handleClose}
            >
              Cancel
            </Button>
            <Button variant="contained" onClick={() => handleConfirm(value)}>
              Confim
            </Button>
          </div>
        </Box>
      </Modal>
    </div>
  );
}
