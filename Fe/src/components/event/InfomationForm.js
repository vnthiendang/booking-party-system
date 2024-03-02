import { Box, Button, TextField } from "@mui/material";
import React, { useState } from "react";
import { useForm, Controller } from "react-hook-form";
import { Bounce, toast } from "react-toastify";
import { ServiceApi } from "../../api";
import ModalRegister from "../ModalRegister";
import ModalLogin from "../ModalLogin";
import ModalConfirm from "../ModalConfirm";

const InfomationForm = ({ handleBooking }) => {
  const {
    handleSubmit,
    control,
    formState: { errors },
  } = useForm({
    mode: "onSubmit",
    defaultValues: {
      email: "",
    },
  });
  const [openRegisterModal, setopenRegisterModal] = useState(false);
  const [openLoginModal, setopenLoginModal] = useState(false);
  const [openConfirmModal, setopenConfirmModal] = useState(false);

  async function onSubmit(data) {
    if (!data.email) {
      toast.error("Email is valid", {
        position: "top-right",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
        transition: Bounce,
      });
    } else {
      const token = sessionStorage.getItem("token")
        ? sessionStorage.getItem("token")
        : "";
      if (token) {
        setopenConfirmModal(true);
      } else {
        const res = await ServiceApi.checkMail(data.email);
        if (typeof res === "boolean") {
          setopenLoginModal(true);
        } else if (typeof res === "object") {
          setopenRegisterModal(true);
        }
      }
    }
  }
  async function onError(error) {
    console.log("🚀 ~ onError ~ errors:", errors);
  }

  return (
    <div>
      <h3>
        Provide us with some additional details to finalize your event. If you
        haven't already logged in, create a new account by entering a password
        below. Then click Next Step to review the cart.
      </h3>
      <form onSubmit={handleSubmit(onSubmit, onError)}>
        <Box sx={{ display: "flex", flexWrap: "wrap" }}>
          <div>
            <Controller
              name="email"
              control={control}
              render={({ field }) => (
                <TextField
                  type="email"
                  label="Email address"
                  id="outlined-start-adornment"
                  sx={{ m: 1, width: "25ch" }}
                  {...field}
                />
              )}
            />
            <TextField
              label="Phone"
              id="outlined-start-adornment"
              sx={{ m: 1, width: "25ch" }}
            />
            <TextField
              label="Name"
              id="outlined-start-adornment"
              sx={{ m: 1, width: "25ch" }}
            />
          </div>
          <Button
            type="submit"
            variant="contained"
            sx={{
              m: 1,
            }}
          >
            {" "}
            Submit
          </Button>
        </Box>
      </form>

      {openRegisterModal && (
        <ModalRegister
          open={openRegisterModal}
          handleClose={() => setopenRegisterModal(false)}
          handleOpenLogiModal={() => setopenLoginModal(true)}
        />
      )}
      {openLoginModal && (
        <ModalLogin
          open={openLoginModal}
          handleClose={() => setopenLoginModal(false)}
          handleOpenConfirmModal={() => setopenConfirmModal(true)}
        />
      )}
      {openConfirmModal && (
        <ModalConfirm
          open={openConfirmModal}
          handleClose={() => setopenConfirmModal(false)}
          handleConfirm={handleBooking}
        />
      )}
    </div>
  );
};

export default InfomationForm;
