import * as React from "react";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import { Button, Stack } from "@mui/material";
import { giftImg } from "../asset";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 600,
  bgcolor: "background.paper",
  //   border: "2px solid #000",
  boxShadow: 24,
  //   p: 4,
};

export default function ModalBuyService({
  open,
  handleClose,
  handleAddService,
}) {
  const { item } = open;
  const [qty, setqty] = React.useState(item?.qty || 1);

  const preventMinus = (e) => {
    if (e.code === "Minus") {
      e.preventDefault();
    }
  };
  return (
    <div>
      <Modal
        open={open.isOpen}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          <Stack direction={"column"}>
            <Box
              sx={{
                background: "#f5f5f5",
                height: "46px",
                textAlign: "center",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                position: "relative",
              }}
            >
              <h4>Product Infomation</h4>
              <Button
                sx={{
                  position: "absolute",
                  right: 0,
                }}
                onClick={handleClose}
              >
                Close
              </Button>
            </Box>
            <Stack
              direction={"row"}
              sx={{
                p: 3,
              }}
            >
              <img src={item?.media} alt="gift" width={200} height={200} />
              <Stack
                direction={"column"}
                sx={{
                  ml: 2,
                }}
              >
                <p
                  style={{
                    lineHeight: "28px",
                  }}
                >
                  {item?.description}
                  <br />${item?.price}
                  <br />
                  {item?.serviceName}
                </p>

                <p>Quantity</p>
                <input
                  type="number"
                  style={{
                    fontSize: "24px",
                  }}
                  onKeyPress={preventMinus}
                  value={qty}
                  min={1}
                  onChange={(e) => {
                    const newValue = e.target.value;
                    console.log(newValue);
                    if (newValue === "") {
                      setqty(""); // Cho phép xóa giá trị
                    } else if (newValue === "0") {
                      setqty(1); // Thay đổi giá trị mặc định thành 2
                    } else {
                      setqty(newValue);
                    }
                  }}
                />

                <Button
                  sx={{
                    marginTop: "10px",
                  }}
                  variant="contained"
                  onClick={() => handleAddService(item.id, qty)}
                >
                  Add service
                </Button>
              </Stack>
            </Stack>
          </Stack>
        </Box>
      </Modal>
    </div>
  );
}
