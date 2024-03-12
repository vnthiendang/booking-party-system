import * as React from "react";
import Box from "@mui/material/Box";
import Stepper from "@mui/material/Stepper";
import Step from "@mui/material/Step";
import StepLabel from "@mui/material/StepLabel";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import { Container } from "@mui/material";
import { LayoutEvent } from "../layout";
import { Bounce, toast } from "react-toastify";
import { listSvImg } from "../asset/listpkImg";

import {
  CustomPackage,
  InfomationForm,
  Order,
  SelectDate,
  SelectPackage,
} from "../components";
import { ServiceApi } from "../api";
import { useNavigate, useParams } from "react-router-dom";
import ModalCancel from "../components/ModalCancel";
import ModalConfirmDeposit from "../components/ModalConfirmDepoist";
import { ROUTER } from "../util";

const steps = [
  // "Select package",
  "Pick a Date",
  "Custom package",
  "Infomation",
  "Order",
];

export default function EventPage() {
  const [activeStep, setActiveStep] = React.useState(0);
  const [skipped, setSkipped] = React.useState(new Set());
  const [packageDetail, setPackageDetail] = React.useState(null);
  const [serviceCustom, setServiceCustom] = React.useState([]);
  const [listService, setListService] = React.useState([]);
  const [totalAmount, setTotalAmount] = React.useState(0);
  const [slot, setslot] = React.useState(0);
  const [time, setTime] = React.useState({});
  const [booking, setBooking] = React.useState(null);
  const [openModalCancel, setopenModalCancel] = React.useState(false);
  const [openModalDeposit, setopenModalDeposit] = React.useState(false);
  const navigate = useNavigate();

  const params = useParams();

  const isStepOptional = (step) => {
    return step === 1;
  };

  const isStepSkipped = (step) => {
    return skipped.has(step);
  };

  const handleNext = () => {
    let newSkipped = skipped;
    if (isStepSkipped(activeStep)) {
      newSkipped = new Set(newSkipped.values());
      newSkipped.delete(activeStep);
    }

    if (activeStep === 3) {
      console.log("confirm deposit");
      setopenModalDeposit(true);
    } else {
      setActiveStep((prevActiveStep) => prevActiveStep + 1);
      setSkipped(newSkipped);
    }
  };

  const handleBack = () => {
    if (activeStep === 3) {
      setopenModalCancel(true);
    } else {
      setActiveStep((prevActiveStep) => prevActiveStep - 1);
    }
  };

  const handleReset = () => {
    setActiveStep(0);
  };

  const handleBooking = async (handleClose) => {
    try {
      const customServices = serviceCustom
        ?.filter((item) => item.choose)
        ?.map((item) => ({ serviceId: item?.id, quantity: item?.qty }));

      const info = sessionStorage.getItem("info")
        ? JSON.parse(sessionStorage.getItem("info"))
        : "";
      const token = sessionStorage.getItem("token")
        ? JSON.parse(sessionStorage.getItem("token"))
        : "";
      const booking = await ServiceApi.bookPackage(
        {
          packageId: packageDetail?.id,
          startTime: time?.startTime,
          endTime: time?.endTime,
          partySize: +slot || 0,
          AddedSizePrice: +slot * 100,
          customerUsId: info?.userId,
        },
        token
      );
      console.log("🚀 ~ handleBooking ~ booking:", booking);
      setBooking(booking?.data);
      await ServiceApi.addService(
        {
          bookingId: booking?.data?.bookingId,
          customServices,
        },
        token
      );
      toast.success("🦄 booking success!", {
        position: "top-right",
        autoClose: 2000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
        transition: Bounce,
      });
      handleClose();
      handleNext();
    } catch (error) {
      toast.error("🦄 Something went wrong!", {
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
    }
  };

  const handleCancelBooking = async (id) => {
    try {
      const token = sessionStorage.getItem("token")
        ? JSON.parse(sessionStorage.getItem("token"))
        : "";
      await ServiceApi.cancelBooking(id, token);
      toast.success("🦄 cancel success!", {
        position: "top-right",
        autoClose: 2000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
        transition: Bounce,
      });
      navigate("/");
    } catch (error) {
      toast.error("🦄 Something went wrong!", {
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
    }
  };

  const handleConfirmDeposit = async (deposit) => {
    try {
      if (+deposit > booking?.totalCost) {
        toast.error("🦄 Deposit must less than or equal cost!", {
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
        return;
      }

      const token = sessionStorage.getItem("token")
        ? JSON.parse(sessionStorage.getItem("token"))
        : "";
      await ServiceApi.addService(
        {
          bookingId: booking?.bookingId,
          deposit: +deposit,
        },
        token
      );
      toast.success("🦄 booking success!", {
        position: "top-right",
        autoClose: 2000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
        transition: Bounce,
      });
      navigate(ROUTER.ORDER_BOOKING);
    } catch (error) {
      toast.error("🦄 Something went wrong!", {
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
    }
  };
  const checkRenderStep = (step) => {
    switch (step) {
      // case 0:
      // return <SelectPackage handleNextStep={handleNext} />;
      case 0:
        return (
          <SelectDate
            handleNext={handleNext}
            packageDetail={packageDetail}
            setTime={setTime}
          />
        );
      case 1:
        return (
          <CustomPackage
            packageDetail={packageDetail}
            serviceCustom={serviceCustom}
            setServiceCustom={setServiceCustom}
            listService={listService}
            setListService={setListService}
            totalAmount={totalAmount}
            slot={slot}
            setslot={setslot}
          />
        );
      case 2:
        return (
          <InfomationForm
            packageDetail={packageDetail}
            handleBooking={handleBooking}
          />
        );
      case 3:
        return (
          <Order
            packageDetail={packageDetail}
            booking={booking}
            serviceCustom={serviceCustom}
          />
        );
      default:
        return <p> Step {step + 1}</p>;
    }
  };

  const getListService = async (id) => {
    try {
      const res = await ServiceApi.getListservicePk(id);
      setListService(
        res
          .map((item, index) => ({ ...item, media: listSvImg[index] }))
          .filter((item) => !item.set)
          .map((item) => ({ ...item, choose: false, qty: 1 }))
      );
      const priceServiceDefault = res
        ?.filter((item) => item.set)
        .reduce((acc, curr) => {
          return acc + curr.price;
        }, 0);

      setTotalAmount((prev) => prev + priceServiceDefault);
    } catch (error) {
      alert(error);
    }
  };
  const getPackagedetail = async (id) => {
    try {
      const res = await ServiceApi.getPackageDetailByCustomer(id);
      setPackageDetail(res);
      setTotalAmount(res?.price);
    } catch (error) {
      alert(error);
    }
  };
  React.useEffect(() => {
    const { id } = params;
    if (id) {
      getPackagedetail(id);
    }
  }, [params.id]);
  React.useEffect(() => {
    getListService(params.id);
  }, [packageDetail]);

  return (
    <LayoutEvent>
      <Container maxWidth="lg">
        <Box sx={{ width: "100%", marginTop: "60px" }}>
          <Stepper activeStep={activeStep}>
            {steps.map((label, index) => {
              const stepProps = {};
              const labelProps = {};
              if (isStepOptional(index)) {
                labelProps.optional = (
                  <Typography variant="caption">Optional</Typography>
                );
              }
              if (isStepSkipped(index)) {
                stepProps.completed = false;
              }
              return (
                <Step key={label} {...stepProps}>
                  <StepLabel {...labelProps}>{label}</StepLabel>
                </Step>
              );
            })}
          </Stepper>
          {activeStep === steps.length ? (
            <React.Fragment>
              <Typography sx={{ mt: 2, mb: 1 }}>
                All steps completed - you&apos;re finished
              </Typography>
              <Box sx={{ display: "flex", flexDirection: "row", pt: 2 }}>
                <Box sx={{ flex: "1 1 auto" }} />
                <Button onClick={handleReset}>Reset</Button>
              </Box>
            </React.Fragment>
          ) : (
            <React.Fragment>
              {checkRenderStep(activeStep)}
              <Box sx={{ display: "flex", flexDirection: "row", pt: 2 }}>
                <Button
                  color="inherit"
                  disabled={activeStep === 0}
                  onClick={handleBack}
                  sx={{ mr: 1 }}
                >
                  {activeStep === steps.length - 1 ? "Cancel" : "Back"}
                </Button>
                <Box sx={{ flex: "1 1 auto" }} />

                {activeStep !== 2 && (
                  <Button onClick={handleNext}>
                    {activeStep === steps.length - 1 ? "Finish" : "Next"}
                  </Button>
                )}
              </Box>
              {openModalCancel && (
                <ModalCancel
                  open={openModalCancel}
                  handleClose={() => setopenModalCancel(false)}
                  handleConfirm={() => handleCancelBooking(booking?.bookingId)}
                />
              )}
            </React.Fragment>
          )}
        </Box>
        {openModalDeposit && (
          <ModalConfirmDeposit
            open={openModalDeposit}
            handleClose={() => setopenModalDeposit(false)}
            handleConfirm={handleConfirmDeposit}
          />
        )}
      </Container>
    </LayoutEvent>
  );
}
